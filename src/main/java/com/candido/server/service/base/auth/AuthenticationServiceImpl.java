package com.candido.server.service.base.auth;

import com.candido.server.config.ConfigAppProperties;
import com.candido.server.domain.v1.account.*;
import com.candido.server.domain.v1.token.Token;
import com.candido.server.domain.v1.token.TokenScopeCategoryEnum;
import com.candido.server.domain.v1.token.TokenTypeEnum;
import com.candido.server.domain.v1.user.User;
import com.candido.server.dto.v1.request.auth.RequestAuthentication;
import com.candido.server.dto.v1.request.auth.RequestPasswordReset;
import com.candido.server.dto.v1.request.auth.RequestRegister;
import com.candido.server.dto.v1.response.auth.ResponseAuthentication;
import com.candido.server.dto.v1.response.auth.ResponseRegistration;
import com.candido.server.event.auth.*;
import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.account.*;
import com.candido.server.exception.security.auth.*;
import com.candido.server.exception.security.jwt.ExceptionInvalidJWTToken;
import com.candido.server.security.config.JwtService;
import com.candido.server.service.base.account.AccountService;
import com.candido.server.service.base.auth.provider.AuthProviderService;
import com.candido.server.service.base.auth.token.TemporaryCodeService;
import com.candido.server.service.base.auth.token.TokenService;
import com.candido.server.service.base.user.UserService;
import com.candido.server.validation.password.PasswordConstraintValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    private final AuthProviderService authProviderService;

    private final AccountService accountService;

    private final ApplicationEventPublisher eventPublisher;

    private final ConfigAppProperties configAppProperties;

    private final UserService userService;

    private final TemporaryCodeService temporaryCodeService;

    @Transactional
    @Override
    public void registerByEmail(RequestRegister request, String ipAddress, String appUrl) {
        var account = accountService.createAccount(request);
        var token = tokenService.createRegistrationToken(account, ipAddress);

        var event = new OnEmailRegistrationEvent(
                this,
                account,
                token.getUuidAccessToken(),
                appUrl
        );
        eventPublisher.publishEvent(event);
    }

    @Transactional
    @Override
    public ResponseRegistration registerByCode(RequestRegister request, String ipAddress, String appUrl) {
        var account = accountService.createAccount(request);
        var token = tokenService.createRegistrationToken(account, ipAddress);
        var temporaryCode = temporaryCodeService.assignCode(token.getId());

        var event = new OnCodeRegistrationEvent(
                this,
                account,
                token.getUuidAccessToken(),
                temporaryCode.getCode(),
                appUrl
        );
        eventPublisher.publishEvent(event);

        return ResponseRegistration
                .builder()
                .sessionId(token.getUuidAccessToken())
                .build();
    }

    @Override
    public void verifyRegistrationByUUIDAccessToken(String uuidAccessToken) {
        int tokenScopeCategoryId = TokenScopeCategoryEnum.BTD_REGISTRATION.getTokenScopeCategoryId();
        Token token = tokenService.findTokenByUUIDAndTokenScopeCategoryIdOrThrow(uuidAccessToken, tokenScopeCategoryId);
        String username = jwtService.extractAndValidateUsername(token.getAccessToken());
        var account = accountService.findAccountByEmailOrThrow(username);
        tokenService.validateToken(token.getAccessToken(), account);
        accountService.activateAccount(account);
        var user = userService.findUserByAccountIdOrThrow(account.getId());
        var event = new OnRegistrationCompletedEvent(this, account, user);
        eventPublisher.publishEvent(event);
    }

    @Override
    public void verifyRegistrationByUUIDAccessTokenAndTemporaryCode(String uuidAccessToken, String temporaryCode) {
        // Recupero il token in base al UUID e allo scopo di registrazione
        Token token = tokenService
                .findByUUIDAndTokenScopeCategoryId(
                        uuidAccessToken,
                        TokenScopeCategoryEnum.BTD_REGISTRATION.getTokenScopeCategoryId()
                )
                .orElseThrow(ExceptionVerifyRegistrationToken::new);

        // Recupero il token di accesso
        String registrationToken = token.getAccessToken();

        // Estraggo lo username dal token
        String username = jwtService.extractUsername(registrationToken);

        // Se lo username è nullo sollevo un'eccezione
        if (username == null)
            throw new ExceptionVerifyRegistrationToken();

        // Recupero l'utente dal database
        var account = accountService
                .findByEmail(username)
                .orElseThrow(() -> new ExceptionAccountNotFound(EnumExceptionName.ACCOUNT_NOT_FOUND.name()));

        // Controllo che il token sia valido altrimenti sollevo un'eccezione
        if (!jwtService.isValidToken(registrationToken, account))
            throw new ExceptionVerifyRegistrationToken();

        // Recupero il codice temporaneo
        var code = temporaryCodeService.findByCode(temporaryCode);

        // Controllo che il codice temporaneo non sia scaduto
        if(code.isEmpty() || code.get().isExpired())
            throw new ExceptionVerifyRegistrationToken();

        // Se l'ID del token è diverso dell'ID del token della sessione
        if(!Objects.equals(code.get().getTokenId(), token.getId()))
            throw new ExceptionVerifyRegistrationToken();

        // Abilito l'account
        account.setStatus(new AccountStatus(AccountStatusEnum.VERIFIED.getStatusId()));
        accountService.save(account);

        // Elimino il token di registrazione
        tokenService.delete(token);

        // Recupero l'utente
        var user = userService.findUserByAccountId(account.getId()).orElse(new User());

        // Invio un evento quando viene confermata la registrazione
        eventPublisher.publishEvent(new OnRegistrationCompletedEvent(this, account, user));
    }

    @Override
    public ResponseAuthentication authenticate(RequestAuthentication request, String ipAddress) {
        // Controllo che l'email e la password non siano vuoti
        if (request.email() == null) throw new ExceptionAuth(EnumExceptionName.EMAIL_CAN_NOT_BE_EMPTY.name());
        if (request.password() == null) throw new ExceptionAuth(EnumExceptionName.PASSWORD_CAN_NOT_BE_EMPTY.name());

        // Se non corretto AuthenticationManager si occupa già di sollevare eccezioni
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        // Se arrivato a questo punto significa che l'utente è corretto quindi recuperiamolo
        var user = accountService
                .findByEmail(request.email())
                .orElseThrow(() -> new ExceptionAccountNotFound(EnumExceptionName.ACCOUNT_NOT_FOUND.name()));

        // Creo un token con i dati dell'utente creato
        var accessToken = jwtService.generateToken(user);

        // Creo il token di refresh
        var refreshToken = jwtService.generateRefreshToken(user);

        // Revoca tutti i token dello user
        tokenService.revokeAllAccountTokens(user);

        // Salva il token dell'utente
        Token token = tokenService.saveUserToken(
                user,
                accessToken,
                refreshToken,
                ipAddress,
                TokenTypeEnum.BEARER,
                TokenScopeCategoryEnum.BTD_RW
        );

        // Ritorno il token appena generato
        return ResponseAuthentication
                .builder()
                .accessToken(accessToken)
                .expiresIn(configAppProperties.getSecurity().getJwt().getExpiration())
                .refreshToken(refreshToken)
                .refreshExpiresIn(configAppProperties.getSecurity().getJwt().getRefreshToken().getExpiration())
                .build();
    }

    @Override
    public ResponseAuthentication refreshToken(HttpServletRequest request, HttpServletResponse response) {
        // Recupero header di autorizzazione
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Variabile del token inviato
        final String refreshToken;

        // Variabile dello username
        final String userEmail;

        // Se header recuperato è null o non inizia con la parola chiave "Bearer ", chiudo la richiesta
        if (authHeader == null || !authHeader.startsWith("Bearer ")) throw new ExceptionInvalidJWTToken();

        // Recupero il token alla settima posizione che è la lunghezza della parola chiave "Bearer "
        refreshToken = authHeader.substring(7);

        System.out.println("JWT Refresh -> {" + refreshToken + "}");

        // Estraggo lo username dal token
        userEmail = jwtService.extractUsername(refreshToken);

        // Se lo username non è nullo
        if (userEmail == null) throw new ExceptionAccountNotFound(EnumExceptionName.ACCOUNT_NOT_FOUND.name());

        // Recupero l'utente dal database
        var user = accountService.findByEmail(userEmail).orElseThrow();

        // Recupero il token
        Optional<Token> optionalToken = tokenService.findByRefreshToken(refreshToken);

        // Controllo che il token sia presente
        if(optionalToken.isEmpty()) {
            throw new ExceptionInvalidJWTToken();
        }

        // Controllo che il token non sia scaduto
        var isValidToken = optionalToken
                .map(t -> !t.isRefreshTokenExpired())
                .orElse(false);

        // Controllo il token
        if (!jwtService.isValidToken(refreshToken, user) || !isValidToken) throw new ExceptionInvalidJWTToken();

        // Genero il token di accesso
        var accessToken = jwtService.generateToken(user);

        // Genero il token di refresh
        var currentRefreshToken = jwtService.generateRefreshToken(user);

        // Revoco tutti i token dell'utente
        tokenService.revokeAllAccountTokens(user);

        // Salvo il token dell'utente
        String ipAddress = request.getRemoteAddr();
        var token = tokenService.saveUserToken(
                user,
                accessToken,
                currentRefreshToken,
                ipAddress,
                TokenTypeEnum.BEARER,
                TokenScopeCategoryEnum.BTD_RW
        );

        // Creo la risposta da inviare
        return ResponseAuthentication
                .builder()
                .accessToken(token.getAccessToken())
                .expiresIn(configAppProperties.getSecurity().getJwt().getExpiration())
                .refreshToken(token.getRefreshToken())
                .refreshExpiresIn(configAppProperties.getSecurity().getJwt().getRefreshToken().getExpiration())
                .build();

    }

    @Override
    public void sendResetPassword(String email, String ipAddress, String appUrl) {
        Optional<Account> account = accountService.findByEmail(email);

        // Se non esiste l'account non solleviamo un'eccezione
        // per evitare che si sappia quale email esistono nel sistema
        // Non controlliamo che sia abilitato per far si che possa attivarsi
        // se non ha completato la registrazione
        if(account.isPresent()) {

            // Creo un token per il reset
            var accessToken = jwtService.generateResetToken(account.get());

            // Salva il token dell'utente
            Token token = tokenService.saveUserToken(
                    account.get(),
                    accessToken,
                    null,
                    ipAddress,
                    TokenTypeEnum.BEARER,
                    TokenScopeCategoryEnum.BTD_RESET
            );

            // Invio un evento quando viene completato il reset
            eventPublisher.publishEvent(new OnResetAccountEvent(this, account.get(), token.getUuidAccessToken(), appUrl));

        }

    }

    @Override
    public ResponseAuthentication resetPassword(String uuidAccessToken, RequestPasswordReset request, String ipAddress) {
        // Recupero il token in base al UUID
        Token token = tokenService
                .findByUUIDAndTokenScopeCategoryId(
                        uuidAccessToken,
                        TokenScopeCategoryEnum.BTD_RESET.getTokenScopeCategoryId()
                )
                .orElseThrow(ExceptionVerifyRegistrationToken::new);

        // Estraggo lo username dal token
        String username = jwtService.extractUsername(token.getAccessToken());

        // Se lo username è nullo sollevo un'eccezione
        if (username == null)
            throw new ExceptionVerifyResetToken();

        // Recupero l'utente dal database
        var account = accountService
                .findByEmail(username)
                .orElseThrow(() -> new ExceptionAccountNotFound(EnumExceptionName.ACCOUNT_NOT_FOUND.name()));

        // Controllo che il token sia valido altrimenti sollevo un'eccezione
        if (!jwtService.isValidToken(token.getAccessToken(), account))
            throw new ExceptionVerifyResetToken();

        // Modifico la password
        PasswordConstraintValidator.isValid(request.password());
        if (!request.password().equals(request.confirmPassword()))
            throw new ExceptionPasswordsDoNotMatch(EnumExceptionName.AUTH_PASSWORDS_DO_NOT_MATCH.name());

        account.setPassword(passwordEncoder.encode(request.password()));

        // Abilito l'account se non è abilitato
        if(account.getStatus().getId() == AccountStatusEnum.PENDING.getStatusId()) {
            account.setStatus(new AccountStatus(AccountStatusEnum.VERIFIED.getStatusId()));
        }

        // Salvo l'account
        accountService.save(account);

        // Elimino il token di reset
        tokenService.delete(token);

        // Invio un evento quando viene resettata la password
        eventPublisher.publishEvent(new OnResetAccountCompletedEvent(this, account));

        // Creo un token con i dati dell'utente
        var accessToken = jwtService.generateToken(account);

        // Creo il token di refresh
        var refreshToken = jwtService.generateRefreshToken(account);

        // Revoca tutti i token dello user
        tokenService.revokeAllAccountTokens(account);

        // Salvo il token dell'utente
        var tokenRW = tokenService.saveUserToken(
                account,
                accessToken,
                refreshToken,
                ipAddress,
                TokenTypeEnum.BEARER,
                TokenScopeCategoryEnum.BTD_RW
        );

        // Creo la risposta da inviare
        return ResponseAuthentication
                .builder()
                .accessToken(tokenRW.getAccessToken())
                .expiresIn(configAppProperties.getSecurity().getJwt().getExpiration())
                .refreshToken(tokenRW.getRefreshToken())
                .refreshExpiresIn(configAppProperties.getSecurity().getJwt().getRefreshToken().getExpiration())
                .build();
    }

    @Override
    public void checkValidityOfUUIDAccessTokenForResetPassword(String uuidAccessToken) {
        // Recupero il token in base al UUID
        Token token = tokenService.findByUUIDAndTokenScopeCategoryId(uuidAccessToken, TokenScopeCategoryEnum.BTD_RESET.getTokenScopeCategoryId())
                .orElseThrow(ExceptionToken::new);

        // Estraggo lo username dal token
        String username = jwtService.extractUsername(token.getAccessToken());

        // Se lo username è nullo sollevo un'eccezione
        if (username == null)
            throw new ExceptionToken();

        // Recupero l'utente dal database
        var account = accountService
                .findByEmail(username)
                .orElseThrow(() -> new ExceptionAccountNotFound(EnumExceptionName.ACCOUNT_NOT_FOUND.name()));

        // Controllo che il token sia valido altrimenti sollevo un'eccezione
        if (!jwtService.isValidToken(token.getAccessToken(), account))
            throw new ExceptionToken();
    }

    @Override
    public void resendCodeRegistrationByUUIDAccessToken(String uuidAccessToken, String appUrl) {
        var token = tokenService
                .findByUUIDAndTokenScopeCategoryId(
                        uuidAccessToken,
                        TokenScopeCategoryEnum.BTD_REGISTRATION.getTokenScopeCategoryId()
                );

        if(token.isEmpty())
            throw new ExceptionToken();

        var account = accountService.findById(token.get().getAccount().getId());

        if(account.isEmpty())
            throw new ExceptionAccountNotFound(EnumExceptionName.ACCOUNT_NOT_FOUND.name());

        temporaryCodeService.findByTokenId(token.get().getId()).ifPresent(temporaryCodeService::delete);

        var temporaryCode = temporaryCodeService.assignCode(token.get().getId());

        // Invio un evento quando viene completata la registrazione
        var event = new OnCodeRegistrationEvent(
                this,
                account.get(),
                token.get().getUuidAccessToken(),
                temporaryCode.getCode(),
                appUrl
        );
        eventPublisher.publishEvent(event);
    }

    @Override
    public void verifyRegistrationByEmail(String email) {
        // TODO: Delete this service
        // Recupero l'utente dal database
        var account = accountService
                .findByEmail(email)
                .orElseThrow(() -> new ExceptionAccountNotFound(EnumExceptionName.ACCOUNT_NOT_FOUND.name()));

        // Abilito l'account
        account.setStatus(new AccountStatus(AccountStatusEnum.VERIFIED.getStatusId()));
        accountService.save(account);
    }

    @Override
    public List<Token> getListOfTokenByEmail(String email) {
        // TODO: Delete this service
        // Recupero l'utente dal database
        var account = accountService
                .findByEmail(email)
                .orElseThrow(() -> new ExceptionAccountNotFound(EnumExceptionName.ACCOUNT_NOT_FOUND.name()));

        return tokenService.findAllValidTokenByUser(account.getId());
    }

}
