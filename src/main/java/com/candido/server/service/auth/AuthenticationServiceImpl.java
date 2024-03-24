package com.candido.server.service.auth;

import com.candido.server.config.AppPropertiesConfig;
import com.candido.server.domain.v1.account.*;
import com.candido.server.domain.v1.provider.AuthProviderEnum;
import com.candido.server.domain.v1.token.Token;
import com.candido.server.domain.v1.token.TokenScopeCategoryEnum;
import com.candido.server.domain.v1.token.TokenTypeEnum;
import com.candido.server.domain.v1.user.User;
import com.candido.server.dto.v1.request.auth.RequestAuthentication;
import com.candido.server.dto.v1.request.auth.RequestPasswordReset;
import com.candido.server.dto.v1.request.auth.RequestRegister;
import com.candido.server.dto.v1.response.auth.ResponseAuthentication;
import com.candido.server.dto.v1.response.auth.ResponseRegistration;
import com.candido.server.event.auth.OnRegistrationCompletedEvent;
import com.candido.server.event.auth.OnRegistrationEvent;
import com.candido.server.event.auth.OnResetAccountCompletedEvent;
import com.candido.server.event.auth.OnResetAccountEvent;
import com.candido.server.exception._common.ExceptionNameEnum;
import com.candido.server.exception.account.*;
import com.candido.server.exception.security.auth.*;
import com.candido.server.exception.security.jwt.InvalidJWTTokenException;
import com.candido.server.security.config.JwtService;
import com.candido.server.service.account.AccountService;
import com.candido.server.service.auth.provider.AuthProviderService;
import com.candido.server.service.auth.token.TemporaryCodeService;
import com.candido.server.service.auth.token.TokenService;
import com.candido.server.service.user.UserService;
import com.candido.server.validation.email.EmailConstraintValidator;
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

import java.time.LocalDateTime;
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

    private final AppPropertiesConfig appPropertiesConfig;

    private final UserService userService;

    private final TemporaryCodeService temporaryCodeService;

    @Transactional
    @Override
    public ResponseRegistration register(RequestRegister request, String ipAddress, String appUrl, boolean isEmailVerification) {
        // Controllo che l'account non esista già
        var duplicateAccount = accountService.findByEmail(request.email());
        if (duplicateAccount.isPresent())
            throw new DuplicateAccountException(ExceptionNameEnum.AUTH_REGISTRATION_DUPLICATE_USERNAME_ACCOUNT.name());

        // Controllo che l'email abbia un formato valido
        if (!EmailConstraintValidator.isValid(request.email()))
            throw new InvalidEmailAccountException(ExceptionNameEnum.INVALID_EMAIL.name());

        // Controllo che la password soddisfi i requisiti minimi
        PasswordConstraintValidator.isValid(request.password());
        if (!request.password().equals(request.confirmPassword()))
            throw new PasswordsDoNotMatchException(ExceptionNameEnum.AUTH_PASSWORDS_DO_NOT_MATCH.name());

        // Creo l'utente con ruolo di USER impostando tutti i campi necessari
        var account = Account
                .builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .accountRole(new AccountRole(AccountRoleEnum.USER.getRoleId()))
                .createdAt(LocalDateTime.now())
                .status(new AccountStatus(AccountStatusEnum.PENDING.getStatusId()))
                .build();

        // Salvo l'utente appena creato
        var savedAccount = accountService.save(account);

        // Creo il profilo dell'utente
        var user = User
                .builder()
                .accountId(savedAccount.getId())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .createdAt(LocalDateTime.now())
                .build();

        // Salvo il profilo dell'utente appena creato
        userService.save(user);

        // Salvo il provider collegato all'account
        authProviderService.addProviderToAccount(AuthProviderEnum.LOCAL.getProviderId(), savedAccount.getId());

        // Creo un token per la verifica della registrazione
        var accessToken = jwtService.generateRegistrationToken(account);

        // Salva il token dell'utente
        var token = tokenService.saveUserToken(
                savedAccount,
                accessToken,
                null,
                ipAddress,
                TokenTypeEnum.BEARER,
                TokenScopeCategoryEnum.BTD_REGISTRATION
        );

        if(!isEmailVerification) {
            var temporaryCode = temporaryCodeService.assignCode(token.getId());

            // Invio un evento quando viene completata la registrazione
            eventPublisher.publishEvent(new OnRegistrationEvent(this, savedAccount, accessToken, temporaryCode.getCode(), appUrl));
        } else {
            // Invio un evento quando viene completata la registrazione
            eventPublisher.publishEvent(new OnRegistrationEvent(this, savedAccount, accessToken, appUrl));
        }

        return ResponseRegistration.builder().sessionId(token.getUuidAccessToken()).build();
    }

    @Override
    public boolean checkEmailAvailability(String email) {
        return accountService.findByEmail(email).isEmpty();
    }

    @Override
    public void verifyRegistrationByToken(String registrationToken) {
        // Estraggo lo username dal token
        String username = jwtService.extractUsername(registrationToken);

        // Se lo username è nullo sollevo un'eccezione
        if (username == null) throw new VerifyRegistrationTokenException();

        // Recupero l'utente dal database
        var account = accountService
                .findByEmail(username)
                .orElseThrow(() -> new AccountNotFoundException(ExceptionNameEnum.ACCOUNT_NOT_FOUND.name()));

        // Controllo che il token sia valido altrimenti sollevo un'eccezione
        if (!jwtService.isValidToken(registrationToken, account)) throw new VerifyRegistrationTokenException();

        // Recupero il token in base all'account ID e allo scopo di registrazione
        Optional<Token> token = tokenService.findByAccountIdAndTokenScopeCategoryId(
                account.getId(), TokenScopeCategoryEnum.BTD_REGISTRATION.getTokenScopeCategoryId()
        );

        // Se il token è presente e uguale a quello che mi è arrivato
        if (token.isEmpty() || !token.get().getAccessToken().equals(registrationToken))
            throw new VerifyRegistrationTokenException();

        // Abilito l'account
        account.setStatus(new AccountStatus(AccountStatusEnum.VERIFIED.getStatusId()));
        accountService.save(account);

        // Elimino il token di registrazione
        tokenService.delete(token.get());

        // Recupero l'utente
        var user = userService.findByAccountId(account.getId()).orElse(new User());

        // Invio un evento quando viene confermata la registrazione
        eventPublisher.publishEvent(new OnRegistrationCompletedEvent(this, account, user));

    }

    @Override
    public void verifyRegistrationBySessionIdAndTemporaryCode(String sessionId, String temporaryCode) {
        // Recupero il token in base alla sessione e allo scopo di registrazione
        Optional<Token> token = tokenService.findByUUIDAndTokenScopeCategoryId(
                sessionId, TokenScopeCategoryEnum.BTD_REGISTRATION.getTokenScopeCategoryId()
        );

        // Se il token è presente e la sessione è uguale
        if (token.isEmpty() || !token.get().getUuidAccessToken().equals(sessionId))
            throw new VerifyRegistrationTokenException();

        // Recupero il token di accesso
        String registrationToken = token.get().getAccessToken();

        // Estraggo lo username dal token
        String username = jwtService.extractUsername(registrationToken);

        // Se lo username è nullo sollevo un'eccezione
        if (username == null) throw new VerifyRegistrationTokenException();

        // Recupero l'utente dal database
        var account = accountService
                .findByEmail(username)
                .orElseThrow(() -> new AccountNotFoundException(ExceptionNameEnum.ACCOUNT_NOT_FOUND.name()));

        // Controllo che il token sia valido altrimenti sollevo un'eccezione
        if (!jwtService.isValidToken(registrationToken, account))
            throw new VerifyRegistrationTokenException();

        // Recupero il codice temporaneo
        var code = temporaryCodeService.findByCode(temporaryCode);

        // Controllo che il codice temporaneo non sia scaduto
        if(code.isEmpty() || code.get().isExpired())
            throw new VerifyRegistrationTokenException();

        // Se l'ID del token è diverso dell'ID del token della sessione
        if(!Objects.equals(code.get().getTokenId(), token.get().getId()))
            throw new VerifyRegistrationTokenException();

        // Abilito l'account
        account.setStatus(new AccountStatus(AccountStatusEnum.VERIFIED.getStatusId()));
        accountService.save(account);

        // Elimino il token di registrazione
        tokenService.delete(token.get());

        // Recupero l'utente
        var user = userService.findByAccountId(account.getId()).orElse(new User());

        // Invio un evento quando viene confermata la registrazione
        eventPublisher.publishEvent(new OnRegistrationCompletedEvent(this, account, user));
    }

    @Override
    public ResponseAuthentication authenticate(RequestAuthentication request, String ipAddress) {
        // Controllo che l'email e la password non siano vuoti
        if (request.email() == null) throw new AuthException(ExceptionNameEnum.EMAIL_CAN_NOT_BE_EMPTY.name());
        if (request.password() == null) throw new AuthException(ExceptionNameEnum.PASSWORD_CAN_NOT_BE_EMPTY.name());

        // Se non corretto AuthenticationManager si occupa già di sollevare eccezioni
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        // Se arrivato a questo punto significa che l'utente è corretto quindi recuperiamolo
        var user = accountService.findByEmail(request.email())
                .orElseThrow();

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
                .expiresIn(appPropertiesConfig.getSecurity().getJwt().getExpiration())
                .refreshToken(refreshToken)
                .refreshExpiresIn(appPropertiesConfig.getSecurity().getJwt().getRefreshToken().getExpiration())
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
        if (authHeader == null || !authHeader.startsWith("Bearer ")) throw new InvalidJWTTokenException();

        // Recupero il token alla settima posizione che è la lunghezza della parola chiave "Bearer "
        refreshToken = authHeader.substring(7);

        System.out.println("JWT Refresh -> {" + refreshToken + "}");

        // Estraggo lo username dal token
        userEmail = jwtService.extractUsername(refreshToken);

        // Se lo username non è nullo
        if (userEmail == null) throw new AccountNotFoundException(ExceptionNameEnum.ACCOUNT_NOT_FOUND.name());

        // Recupero l'utente dal database
        var user = accountService.findByEmail(userEmail).orElseThrow();

        // Recupero il token
        Optional<Token> optionalToken = tokenService.findByRefreshToken(refreshToken);

        // Controllo che il token sia presente
        if(optionalToken.isEmpty()) {
            throw new InvalidJWTTokenException();
        }

        // Controllo che il token non sia scaduto
        var isValidToken = optionalToken
                .map(t -> !t.isRefreshTokenExpired())
                .orElse(false);

        // Controllo il token
        if (!jwtService.isValidToken(refreshToken, user) || !isValidToken) throw new InvalidJWTTokenException();

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
                .expiresIn(appPropertiesConfig.getSecurity().getJwt().getExpiration())
                .refreshToken(token.getRefreshToken())
                .refreshExpiresIn(appPropertiesConfig.getSecurity().getJwt().getRefreshToken().getExpiration())
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
            tokenService.saveUserToken(
                    account.get(),
                    accessToken,
                    null,
                    ipAddress,
                    TokenTypeEnum.BEARER,
                    TokenScopeCategoryEnum.BTD_RESET
            );

            // Invio un evento quando viene completato il reset
            eventPublisher.publishEvent(new OnResetAccountEvent(this, account.get(), accessToken, appUrl));

        }

    }

    @Override
    public ResponseAuthentication resetPassword(String resetToken, RequestPasswordReset request, String ipAddress) {
        // Estraggo lo username dal token
        String username = jwtService.extractUsername(resetToken);

        // Se lo username è nullo sollevo un'eccezione
        if (username == null) throw new VerifyResetTokenException();

        // Recupero l'utente dal database
        var account = accountService
                .findByEmail(username)
                .orElseThrow(() -> new AccountNotFoundException(ExceptionNameEnum.ACCOUNT_NOT_FOUND.name()));

        // Controllo che il token sia valido altrimenti sollevo un'eccezione
        if (!jwtService.isValidToken(resetToken, account)) throw new VerifyResetTokenException();

        // Recupero il token in base all'account ID e allo scopo di reset
        Optional<Token> tokenReset = tokenService.findByAccountIdAndTokenScopeCategoryId(
                account.getId(), TokenScopeCategoryEnum.BTD_RESET.getTokenScopeCategoryId()
        );

        // Se il token è presente e uguale a quello che mi è arrivato
        if (tokenReset.isEmpty() || !tokenReset.get().getAccessToken().equals(resetToken))
            throw new VerifyRegistrationTokenException();

        // Modifico la password
        PasswordConstraintValidator.isValid(request.password());
        if (!request.password().equals(request.confirmPassword()))
            throw new PasswordsDoNotMatchException(ExceptionNameEnum.AUTH_PASSWORDS_DO_NOT_MATCH.name());

        account.setPassword(passwordEncoder.encode(request.password()));

        // Abilito l'account se non è abilitato
        if(account.getStatus().getId() == AccountStatusEnum.PENDING.getStatusId()) {
            account.setStatus(new AccountStatus(AccountStatusEnum.VERIFIED.getStatusId()));
        }

        // Salvo l'account
        accountService.save(account);

        // Elimino il token di reset
        tokenService.delete(tokenReset.get());

        // Invio un evento quando viene resettata la password
        eventPublisher.publishEvent(new OnResetAccountCompletedEvent(this, account));

        // Creo un token con i dati dell'utente
        var accessToken = jwtService.generateToken(account);

        // Creo il token di refresh
        var refreshToken = jwtService.generateRefreshToken(account);

        // Revoca tutti i token dello user
        tokenService.revokeAllAccountTokens(account);

        // Salvo il token dell'utente
        var token = tokenService.saveUserToken(
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
                .accessToken(token.getAccessToken())
                .expiresIn(appPropertiesConfig.getSecurity().getJwt().getExpiration())
                .refreshToken(token.getRefreshToken())
                .refreshExpiresIn(appPropertiesConfig.getSecurity().getJwt().getRefreshToken().getExpiration())
                .build();
    }

    @Override
    public Account getAccountAndVerifyToken(String token, int tokenScopeCategoryId) {
        // Estraggo lo username dal token
        String username = jwtService.extractUsername(token);

        // Se lo username è nullo sollevo un'eccezione
        if (username == null) throw new TokenException();

        // Recupero l'utente dal database
        var account = accountService
                .findByEmail(username)
                .orElseThrow(() -> new AccountNotFoundException(ExceptionNameEnum.ACCOUNT_NOT_FOUND.name()));

        // Controllo che il token sia valido altrimenti sollevo un'eccezione
        if (!jwtService.isValidToken(token, account)) throw new TokenException();

        // Recupero il token in base all'account ID e allo scopo di reset
        Optional<Token> optionalToken = tokenService.findByAccountIdAndTokenScopeCategoryId(
                account.getId(), tokenScopeCategoryId
        );

        // Se il token è presente e uguale a quello che mi è arrivato
        if (optionalToken.isEmpty() || !optionalToken.get().getAccessToken().equals(token))
            throw new TokenException();

        return account;
    }

    @Override
    public void resendCodeRegistrationBySessionId(String sessionId, String appUrl) {
        var token = tokenService
                .findByUUIDAndTokenScopeCategoryId(sessionId, TokenScopeCategoryEnum.BTD_REGISTRATION.getTokenScopeCategoryId());

        if(token.isEmpty()) throw new TokenException();

        var account = accountService.findById(token.get().getAccount().getId());

        if(account.isEmpty()) throw new AccountNotFoundException(ExceptionNameEnum.ACCOUNT_NOT_FOUND.name());

        temporaryCodeService.findByTokenId(token.get().getId()).ifPresent(temporaryCodeService::delete);

        var temporaryCode = temporaryCodeService.assignCode(token.get().getId());

        // Invio un evento quando viene completata la registrazione
        eventPublisher.publishEvent(new OnRegistrationEvent(this, account.get(), token.get().getAccessToken(), temporaryCode.getCode(), appUrl));
    }

    @Override
    public void verifyRegistrationByEmail(String email) {
        // TODO: Delete this service
        // Recupero l'utente dal database
        var account = accountService
                .findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException(ExceptionNameEnum.ACCOUNT_NOT_FOUND.name()));

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
                .orElseThrow(() -> new AccountNotFoundException(ExceptionNameEnum.ACCOUNT_NOT_FOUND.name()));

        return tokenService.findAllValidTokenByUser(account.getId());
    }

}
