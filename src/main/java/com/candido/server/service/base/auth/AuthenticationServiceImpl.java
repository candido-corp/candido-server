package com.candido.server.service.base.auth;

import com.candido.server.config.ConfigAppProperties;
import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.account.AccountStatus;
import com.candido.server.domain.v1.account.AccountStatusEnum;
import com.candido.server.domain.v1.token.Token;
import com.candido.server.domain.v1.token.TokenScopeCategoryEnum;
import com.candido.server.domain.v1.user.User;
import com.candido.server.dto.v1.request.auth.RequestAuthentication;
import com.candido.server.dto.v1.request.auth.RequestPasswordReset;
import com.candido.server.dto.v1.request.auth.RequestRegister;
import com.candido.server.dto.v1.response.auth.ResponseAuthentication;
import com.candido.server.dto.v1.response.auth.ResponseRegistration;
import com.candido.server.dto.v1.util.AccountUserPairDto;
import com.candido.server.event.auth.*;
import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.account.ExceptionAccountNotFound;
import com.candido.server.exception.security.auth.ExceptionAuth;
import com.candido.server.exception.security.auth.ExceptionInvalidToken;
import com.candido.server.exception.security.jwt.ExceptionInvalidJWTToken;
import com.candido.server.security.JwtService;
import com.candido.server.service.base.account.AccountService;
import com.candido.server.service.base.auth.token.TemporaryCodeService;
import com.candido.server.service.base.auth.token.TokenService;
import com.candido.server.service.base.user.UserService;
import com.candido.server.util.EncryptionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
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

    private final AccountService accountService;

    private final ApplicationEventPublisher eventPublisher;

    private final ConfigAppProperties configAppProperties;

    private final UserService userService;

    private final TemporaryCodeService temporaryCodeService;

    private final EncryptionService encryptionService;

    @Transactional
    @Override
    public ResponseAuthentication registerByEmail(RequestRegister request, String ipAddress, String appUrl) {
        AccountUserPairDto accountUserPair = accountService.createAccount(request);
        Token token = tokenService.createRegistrationToken(accountUserPair.account(), ipAddress);
        String encryptedEmail = encryptionService.encrypt(request.email());

        var event = new OnEmailRegistrationEvent(
                this,
                accountUserPair.account(),
                accountUserPair.user(),
                token.getUuidAccessToken(),
                appUrl,
                encryptedEmail
        );
        eventPublisher.publishEvent(event);

        return createAuthentication(accountUserPair.account(), ipAddress, token);
    }

    @Transactional
    @Override
    public ResponseRegistration registerByCode(RequestRegister request, String ipAddress, String appUrl) {
        var accountUserPair = accountService.createAccount(request);
        var token = tokenService.createRegistrationToken(accountUserPair.account(), ipAddress);
        var temporaryCode = temporaryCodeService.assignCode(token.getId());
        var encryptedEmail = encryptionService.encrypt(request.email());

        var event = new OnCodeRegistrationEvent(
                this,
                accountUserPair.account(),
                accountUserPair.user(),
                token.getUuidAccessToken(),
                temporaryCode.getCode(),
                appUrl,
                encryptedEmail
        );
        eventPublisher.publishEvent(event);

        return ResponseRegistration
                .builder()
                .t(token.getUuidAccessToken())
                .e(encryptedEmail)
                .build();
    }

    @Transactional
    @Override
    public ResponseAuthentication verifyEmailRegistration(String uuidAccessToken, String email, String ipAddress) {
        int tokenScopeCategoryId = TokenScopeCategoryEnum.REGISTRATION.getTokenScopeCategoryId();
        Token token = tokenService.findTokenByUUIDAndTokenScopeCategoryIdOrThrow(uuidAccessToken, tokenScopeCategoryId);
        var account = accountService.findByEmail(email);

        if(account.isEmpty() || !Objects.equals(token.getAccount().getId(), account.get().getId()))
            throw new ExceptionAuth(EnumExceptionName.ERROR_AUTH_VERIFICATION.name());

        tokenService.validateTokenAndDelete(token.getAccessToken(), tokenScopeCategoryId, account.get());
        accountService.activateAccount(account.get());
        var user = userService.findUserByAccountIdOrThrow(account.get().getId());
        var event = new OnRegistrationCompletedEvent(this, account.get(), user, token.getIpAddress());
        eventPublisher.publishEvent(event);

        return createAuthentication(account.get(), ipAddress, null);
    }

    @Transactional
    @Override
    public ResponseAuthentication verifyCodeRegistration(String uuidAccessToken, String temporaryCode, String email, String ipAddress) {
        int tokenScopeCategoryId = TokenScopeCategoryEnum.REGISTRATION.getTokenScopeCategoryId();
        Token token = tokenService.findTokenByUUIDAndTokenScopeCategoryIdOrThrow(uuidAccessToken, tokenScopeCategoryId);
        var account = accountService.findByEmail(email);

        if(account.isEmpty() || !Objects.equals(token.getAccount().getId(), account.get().getId()))
            throw new ExceptionAuth(EnumExceptionName.ERROR_AUTH_VERIFICATION.name());

        temporaryCodeService.validateTemporaryCode(temporaryCode, token.getId());
        tokenService.validateTokenAndDelete(token.getAccessToken(), tokenScopeCategoryId, account.get());
        accountService.activateAccount(account.get());
        var user = userService.findUserByAccountIdOrThrow(account.get().getId());
        var event = new OnRegistrationCompletedEvent(this, account.get(), user, ipAddress);
        eventPublisher.publishEvent(event);

        return createAuthentication(account.get(), ipAddress, null);
    }

    @Override
    public ResponseAuthentication authenticate(RequestAuthentication request, String ipAddress) {
        Authentication auth = authenticationManager.authenticate(request.toUsernamePasswordAuthenticationToken());
        Account account = (Account)  auth.getPrincipal();
        return createAuthentication(account, ipAddress, null);
    }

    @Override
    public ResponseAuthentication refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = getTokenFromAuthorizationHeaderRequest(request);

        String username = jwtService.extractUsername(refreshToken);
        var account = accountService.findAccountByEmailOrThrow(username);
        Token currToken = tokenService.findTokenByRefreshTokenOrThrow(refreshToken);

        if (!jwtService.isValidToken(currToken.getRefreshToken(), account) || currToken.isRefreshTokenExpired())
            throw new ExceptionInvalidJWTToken();

        String ipAddress = request.getRemoteAddr();
        return createAuthentication(account, ipAddress);
    }

    @Override
    public void sendResetPassword(String email, String ipAddress, String appUrl) {
        Optional<Account> account = accountService.findByEmail(email);

        // If the account doesn't exist, we don't throw an exception
        // to avoid revealing which emails are registered in the system.

        // We don't check whether the account is enabled,
        // allowing it to be activated even if registration isn't complete.
        if(account.isPresent() && account.get().isEnabled()) {
            Token token = tokenService.createResetToken(account.get(), ipAddress);
            Optional<User> user = userService.findUserByAccountId(account.get().getId());
            if(user.isEmpty()) return;
            var encryptedEmail = encryptionService.encrypt(account.get().getEmail());
            var event = new OnResetAccountEvent(
                    this,
                    account.get(),
                    user.get(),
                    token.getUuidAccessToken(),
                    appUrl,
                    encryptedEmail
            );
            eventPublisher.publishEvent(event);
        }
    }

    @Override
    public ResponseAuthentication resetPassword(String uuidAccessToken, String email, RequestPasswordReset request, String ipAddress) {
        int tokenScopeCategoryId = TokenScopeCategoryEnum.RESET.getTokenScopeCategoryId();
        Token currToken = tokenService.findTokenByUUIDAndTokenScopeCategoryIdOrThrow(uuidAccessToken, tokenScopeCategoryId);
        var account = accountService.findByEmail(email);

        if(account.isEmpty() || !Objects.equals(currToken.getAccount().getId(), account.get().getId()))
            throw new ExceptionAuth(EnumExceptionName.ERROR_AUTH_VERIFICATION.name());

        tokenService.validateTokenAndDelete(currToken.getAccessToken(), tokenScopeCategoryId, account.get());

        account.get().setPassword(passwordEncoder.encode(request.password()));
        if(account.get().getStatus().getId() == AccountStatusEnum.PENDING.getStatusId()) {
            account.get().setStatus(new AccountStatus(AccountStatusEnum.VERIFIED.getStatusId()));
        }
        accountService.save(account.get());

        User user = userService.findUserByAccountIdOrThrow(account.get().getId());

        var event = new OnResetAccountCompletedEvent(this, account.get(), user);
        eventPublisher.publishEvent(event);

        Token token = tokenService.createLoginToken(account.get(), ipAddress);

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
        int tokenScopeCategoryId = TokenScopeCategoryEnum.RESET.getTokenScopeCategoryId();
        var token = tokenService.findByUUIDAndTokenScopeCategoryId(uuidAccessToken, tokenScopeCategoryId);
        if(token.isEmpty()) throw new ExceptionInvalidToken(EnumExceptionName.ERROR_VALIDATION_INVALID_TOKEN.name());
        String username = jwtService.extractAndValidateUsername(token.get().getAccessToken());
        var account = accountService.findAccountByEmailOrThrow(username);
        if (!jwtService.isValidToken(token.get().getAccessToken(), account))
            throw new ExceptionInvalidJWTToken();
    }

    @Override
    public void resendEmailRegistration(String email, String appUrl, String ipAddress) {
        Optional<Account> account = accountService.findByEmail(email);

        if(account.isPresent() && account.get().isEnabled()) {
            Token token = tokenService.createRegistrationToken(account.get(), ipAddress);
            Optional<User> user = userService.findUserByAccountId(account.get().getId());
            if (user.isEmpty()) return;
            var encryptedEmail = encryptionService.encrypt(account.get().getEmail());
            var event = new OnEmailRegistrationEvent(
                this,
                account.get(),
                user.get(),
                token.getUuidAccessToken(),
                appUrl,
                encryptedEmail
            );
            eventPublisher.publishEvent(event);
        }
    }

    @Override
    public void resendCodeRegistrationByUUIDAccessToken(String uuidAccessToken, String appUrl) {
        int tokenScopeCategoryId = TokenScopeCategoryEnum.REGISTRATION.getTokenScopeCategoryId();
        var token = tokenService.findTokenByUUIDAndTokenScopeCategoryIdOrThrow(uuidAccessToken, tokenScopeCategoryId);
        var account = accountService.findAccountByIdOrThrow(token.getAccount().getId());
        temporaryCodeService.deleteTemporaryCodeByTokenId(token.getId());
        var temporaryCode = temporaryCodeService.assignCode(token.getId());
        var user = userService.findUserByAccountIdOrThrow(account.getId());
        var encryptedEmail = encryptionService.encrypt(account.getEmail());
        var event = new OnCodeRegistrationEvent(
                this,
                account,
                user,
                token.getUuidAccessToken(),
                temporaryCode.getCode(),
                appUrl,
                encryptedEmail
        );
        eventPublisher.publishEvent(event);
    }

    @Override
    public ResponseAuthentication createAuthentication(Account account, String ipAddress, Token token) {
        token = token != null ? token : tokenService.createLoginToken(account, ipAddress);

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
    public void temp_verifyRegistrationByEmail(String email) {
        // TODO: Delete this service
        // Recupero l'utente dal database
        var account = accountService
                .findByEmail(email)
                .orElseThrow(() -> new ExceptionAccountNotFound(EnumExceptionName.ERROR_BUSINESS_ACCOUNT_NOT_FOUND.name()));

        // Abilito l'account
        account.setStatus(new AccountStatus(AccountStatusEnum.VERIFIED.getStatusId()));
        accountService.save(account);
    }

    @Override
    public List<Token> temp_getListOfTokenByEmail(String email) {
        // TODO: Delete this service
        // Recupero l'utente dal database
        var account = accountService
                .findByEmail(email)
                .orElseThrow(() -> new ExceptionAccountNotFound(EnumExceptionName.ERROR_BUSINESS_ACCOUNT_NOT_FOUND.name()));

        return tokenService.findAllValidTokenByUser(account.getId());
    }

}
