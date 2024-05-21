package com.candido.server.service.base.auth;

import com.candido.server.config.ConfigAppProperties;
import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.account.AccountStatus;
import com.candido.server.domain.v1.account.AccountStatusEnum;
import com.candido.server.domain.v1.token.Token;
import com.candido.server.domain.v1.token.TokenScopeCategoryEnum;
import com.candido.server.dto.v1.request.auth.RequestAuthentication;
import com.candido.server.dto.v1.request.auth.RequestPasswordReset;
import com.candido.server.dto.v1.request.auth.RequestRegister;
import com.candido.server.dto.v1.response.auth.ResponseAuthentication;
import com.candido.server.dto.v1.response.auth.ResponseRegistration;
import com.candido.server.event.auth.*;
import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.account.ExceptionAccountNotFound;
import com.candido.server.exception.security.jwt.ExceptionInvalidJWTToken;
import com.candido.server.security.config.JwtService;
import com.candido.server.service.base.account.AccountService;
import com.candido.server.service.base.auth.token.TemporaryCodeService;
import com.candido.server.service.base.auth.token.TokenService;
import com.candido.server.service.base.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

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

    @Transactional
    @Override
    public void verifyRegistrationByUUIDAccessToken(String uuidAccessToken) {
        int tokenScopeCategoryId = TokenScopeCategoryEnum.BTD_REGISTRATION.getTokenScopeCategoryId();
        Token token = tokenService.findTokenByUUIDAndTokenScopeCategoryIdOrThrow(uuidAccessToken, tokenScopeCategoryId);
        String username = jwtService.extractAndValidateUsername(token.getAccessToken());
        var account = accountService.findAccountByEmailOrThrow(username);
        tokenService.validateTokenAndDelete(token.getAccessToken(), tokenScopeCategoryId, account);
        accountService.activateAccount(account);
        var user = userService.findUserByAccountIdOrThrow(account.getId());
        var event = new OnRegistrationCompletedEvent(this, account, user);
        eventPublisher.publishEvent(event);
    }

    @Transactional
    @Override
    public void verifyRegistrationByUUIDAccessTokenAndTemporaryCode(String uuidAccessToken, String temporaryCode) {
        int tokenScopeCategoryId = TokenScopeCategoryEnum.BTD_REGISTRATION.getTokenScopeCategoryId();
        Token token = tokenService.findTokenByUUIDAndTokenScopeCategoryIdOrThrow(uuidAccessToken, tokenScopeCategoryId);
        String username = jwtService.extractAndValidateUsername(token.getAccessToken());
        var account = accountService.findAccountByEmailOrThrow(username);
        temporaryCodeService.validateTemporaryCode(temporaryCode, token.getId());
        tokenService.validateTokenAndDelete(token.getAccessToken(), tokenScopeCategoryId, account);
        accountService.activateAccount(account);
        var user = userService.findUserByAccountIdOrThrow(account.getId());
        var event = new OnRegistrationCompletedEvent(this, account, user);
        eventPublisher.publishEvent(event);
    }

    @Override
    public ResponseAuthentication authenticate(RequestAuthentication request, String ipAddress) {
        request.checkFields();
        authenticationManager.authenticate(request.toUsernamePasswordAuthenticationToken());
        var account = accountService.findAccountByEmailOrThrow(request.email());
        Token token = tokenService.createLoginToken(account, ipAddress);

        var expires = configAppProperties.getSecurity().getJwt().getExpiration();
        var refreshExpires = configAppProperties.getSecurity().getJwt().getRefreshToken().getExpiration();

        return ResponseAuthentication
                .builder()
                .accessToken(token.getAccessToken())
                .expiresIn(expires)
                .refreshToken(token.getRefreshToken())
                .refreshExpiresIn(refreshExpires)
                .build();
    }

    @Override
    public ResponseAuthentication refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = getTokenFromAuthorizationHeaderRequest(request);

        String username = jwtService.extractUsername(refreshToken);
        var account = accountService.findAccountByEmailOrThrow(username);
        Token currToken = tokenService.findTokenByRefreshTokenOrThrow(refreshToken);

        if (!jwtService.isValidToken(currToken.getRefreshToken(), account) || !currToken.isRefreshTokenExpired())
            throw new ExceptionInvalidJWTToken();

        String ipAddress = request.getRemoteAddr();
        Token token = tokenService.createLoginToken(account, ipAddress);

        var expires = configAppProperties.getSecurity().getJwt().getExpiration();
        var refreshExpires = configAppProperties.getSecurity().getJwt().getRefreshToken().getExpiration();

        return ResponseAuthentication
                .builder()
                .accessToken(token.getAccessToken())
                .expiresIn(expires)
                .refreshToken(token.getRefreshToken())
                .refreshExpiresIn(refreshExpires)
                .build();
    }

    @Override
    public void sendResetPassword(String email, String ipAddress, String appUrl) {
        Optional<Account> account = accountService.findByEmail(email);

        // Se non esiste l'account non solleviamo un'eccezione
        // per evitare che si sappia quale email esistono nel sistema
        // Non controlliamo che sia abilitato per far si che possa attivarsi
        // se non ha completato la registrazione
        if(account.isPresent() && account.get().isEnabled()) {
            Token token = tokenService.createResetToken(account.get(), ipAddress);
            var event = new OnResetAccountEvent(this, account.get(), token.getUuidAccessToken(), appUrl);
            eventPublisher.publishEvent(event);
        }
    }

    @Override
    public ResponseAuthentication resetPassword(String uuidAccessToken, RequestPasswordReset request, String ipAddress) {
        request.checkFields();
        int tokenScopeCategoryId = TokenScopeCategoryEnum.BTD_RESET.getTokenScopeCategoryId();
        Token currToken = tokenService.findTokenByUUIDAndTokenScopeCategoryIdOrThrow(uuidAccessToken, tokenScopeCategoryId);
        String username = jwtService.extractAndValidateUsername(currToken.getAccessToken());
        var account = accountService.findAccountByEmailOrThrow(username);
        tokenService.validateTokenAndDelete(currToken.getAccessToken(), tokenScopeCategoryId, account);

        account.setPassword(passwordEncoder.encode(request.password()));
        if(account.getStatus().getId() == AccountStatusEnum.PENDING.getStatusId()) {
            account.setStatus(new AccountStatus(AccountStatusEnum.VERIFIED.getStatusId()));
        }
        accountService.save(account);

        var event = new OnResetAccountCompletedEvent(this, account);
        eventPublisher.publishEvent(event);

        Token token = tokenService.createLoginToken(account, ipAddress);

        var expires = configAppProperties.getSecurity().getJwt().getExpiration();
        var refreshExpires = configAppProperties.getSecurity().getJwt().getRefreshToken().getExpiration();

        return ResponseAuthentication
                .builder()
                .accessToken(token.getAccessToken())
                .expiresIn(expires)
                .refreshToken(token.getRefreshToken())
                .refreshExpiresIn(refreshExpires)
                .build();
    }

    @Override
    public String getTokenFromAuthorizationHeaderRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) throw new ExceptionInvalidJWTToken();
        return authHeader.substring(7);
    }

    @Override
    public void checkValidityOfUUIDAccessTokenForResetPassword(String uuidAccessToken) {
        Token token = tokenService.findTokenByUUIDAndTokenScopeCategoryIdOrThrow(
                uuidAccessToken, TokenScopeCategoryEnum.BTD_RESET.getTokenScopeCategoryId()
        );
        String username = jwtService.extractAndValidateUsername(token.getAccessToken());
        var account = accountService.findAccountByEmailOrThrow(username);
        if (!jwtService.isValidToken(token.getAccessToken(), account))
            throw new ExceptionInvalidJWTToken();
    }

    @Override
    public void resendCodeRegistrationByUUIDAccessToken(String uuidAccessToken, String appUrl) {
        int tokenScopeCategoryId = TokenScopeCategoryEnum.BTD_REGISTRATION.getTokenScopeCategoryId();
        var token = tokenService.findTokenByUUIDAndTokenScopeCategoryIdOrThrow(uuidAccessToken, tokenScopeCategoryId);
        var account = accountService.findAccountByIdOrThrow(token.getAccount().getId());
        temporaryCodeService.deleteTemporaryCodeByTokenId(token.getId());
        var temporaryCode = temporaryCodeService.assignCode(token.getId());
        var event = new OnCodeRegistrationEvent(
                this,
                account,
                token.getUuidAccessToken(),
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
