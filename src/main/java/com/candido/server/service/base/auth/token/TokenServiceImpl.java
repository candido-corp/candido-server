package com.candido.server.service.base.auth.token;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.token.*;
import com.candido.server.exception.security.auth.ExceptionToken;
import com.candido.server.exception.security.jwt.ExceptionInvalidJWTToken;
import com.candido.server.security.JwtService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
@Transactional(isolation = Isolation.SERIALIZABLE)
public class TokenServiceImpl implements TokenService {

    private final ConcurrentHashMap<Integer, ReentrantLock> userLocks = new ConcurrentHashMap<>();

    private final JwtService jwtService;

    private final TokenRepository tokenRepository;

    private final TemporaryCodeService temporaryCodeService;

    @Autowired
    public TokenServiceImpl(
            JwtService jwtService,
            TokenRepository tokenRepository,
            TemporaryCodeService temporaryCodeService
    ) {
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.temporaryCodeService = temporaryCodeService;
    }

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<Token> findByAccessToken(String accessToken) {
        return tokenRepository.findByAccessToken(accessToken);
    }

    @Override
    public Optional<Token> findByRefreshToken(String refreshToken) {
        return tokenRepository.findByRefreshToken(refreshToken);
    }

    @Override
    public Optional<Token> findByUUIDAndTokenScopeCategoryId(String uuid, int tokenScopeCategoryId) {
        return tokenRepository.findByUuidAccessTokenAndTokenScopeCategoryId(uuid, tokenScopeCategoryId);
    }

    @Override
    public Optional<Token> findByAccountIdAndTokenScopeCategoryId(int accountId, int tokenScopeCategoryId) {
        return tokenRepository.findByAccountIdAndTokenScopeCategoryId(accountId, tokenScopeCategoryId);
    }

    @Override
    public Token findTokenByUUIDAndTokenScopeCategoryIdOrThrow(String uuid, int tokenScopeCategoryId) {
        return findByUUIDAndTokenScopeCategoryId(uuid, tokenScopeCategoryId).orElseThrow(ExceptionToken::new);
    }

    @Override
    public Token findTokenByRefreshTokenOrThrow(String refreshToken) {
        return findByRefreshToken(refreshToken).orElseThrow(ExceptionToken::new);
    }

    @Override
    public Token saveUserToken(
            Account account,
            String accessToken,
            String refreshToken,
            String ipAddress,
            TokenTypeEnum tokenType,
            TokenScopeCategoryEnum tokenScopeCategoryEnum
    ) {
        ReentrantLock lock = userLocks.computeIfAbsent(account.getId(), k -> new ReentrantLock());
        lock.lock();
        try {
            revokeAllAccountTokensByTokenScopeCategoryId(account, tokenScopeCategoryEnum.getTokenScopeCategoryId());
            entityManager.flush();

            Date accessTokenExpirationDate = jwtService.extractExpiration(accessToken);
            LocalDateTime accessTokenExpiration = Instant.ofEpochMilli(accessTokenExpirationDate.getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            String uuidAccessToken = UUID.randomUUID().toString().replace("-", "");

            var token = Token
                    .builder()
                    .account(account)
                    .accessToken(accessToken)
                    .tokenType(new TokenType(tokenType.getTokenTypeId()))
                    .accessTokenExpiration(accessTokenExpiration)
                    .tokenScopeCategory(new TokenScopeCategory(tokenScopeCategoryEnum.getTokenScopeCategoryId()))
                    .ipAddress(ipAddress)
                    .uuidAccessToken(uuidAccessToken)
                    .valid(true)
                    .build();

            if (refreshToken != null) {
                Date refreshTokenExpirationDate = jwtService.extractExpiration(refreshToken);
                LocalDateTime refreshTokenExpiration = Instant.ofEpochMilli(refreshTokenExpirationDate.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
                token.setRefreshToken(refreshToken);
                token.setRefreshTokenExpiration(refreshTokenExpiration);
            }

            return tokenRepository.save(token);
        } catch (Exception e) {
            throw new ExceptionToken();
        } finally {
            lock.unlock();
            userLocks.remove(account.getId(), lock);
        }
    }

    @Override
    public void delete(Token token) {
        token.setValid(false);
        tokenRepository.save(token);
    }

    @Override
    public List<Token> findAllValidTokenByUser(Integer accountId) {
        return tokenRepository.findAllByAccountId(accountId);
    }

    @Override
    public void revokeAllAccountTokens(Account account) {
        var validAccountTokens = findAllValidTokenByUser(account.getId());
        if (validAccountTokens.isEmpty()) return;
        validAccountTokens.forEach(this::delete);
        entityManager.flush();
    }

    @Override
    public void revokeAllAccountTokensByTokenScopeCategoryId(Account account, int tokenScopeCategoryId) {
        var validAccountTokens = findAllValidTokenByUser(account.getId());
        if (validAccountTokens.isEmpty()) return;
        validAccountTokens.stream()
                .filter(token -> token.getTokenScopeCategory().getId() == tokenScopeCategoryId)
                .forEach(this::delete);
        entityManager.flush();
    }

    @Override
    public Token createRegistrationToken(Account account, String ipAddress) {
        var accessToken = jwtService.generateRegistrationToken(account);
        return saveUserToken(
                account,
                accessToken,
                null,
                ipAddress,
                TokenTypeEnum.BEARER,
                TokenScopeCategoryEnum.REGISTRATION
        );
    }

    @Override
    public Token createLoginToken(Account account, String ipAddress) {
        var accessToken = jwtService.generateToken(account);
        var refreshToken = jwtService.generateRefreshToken(account);
        return saveUserToken(
                account,
                accessToken,
                refreshToken,
                ipAddress,
                TokenTypeEnum.BEARER,
                TokenScopeCategoryEnum.RW
        );
    }

    @Override
    public Token createResetToken(Account account, String ipAddress) {
        var accessToken = jwtService.generateResetToken(account);
        return saveUserToken(
                account,
                accessToken,
                null,
                ipAddress,
                TokenTypeEnum.BEARER,
                TokenScopeCategoryEnum.RESET
        );
    }

    @Override
    public void validateTokenAndDelete(String token, int tokenScopeCategoryId, Account account) {
        if (!jwtService.isValidToken(token, account)) throw new ExceptionInvalidJWTToken();

        Optional<Token> currToken = findByAccountIdAndTokenScopeCategoryId(account.getId(), tokenScopeCategoryId);

        boolean isCurrTokenPresent = currToken.isPresent();
        boolean isCurrTokenEqualsToToken = isCurrTokenPresent && currToken.get().getAccessToken().equals(token);
        if (!isCurrTokenPresent || !isCurrTokenEqualsToToken) throw new ExceptionToken();

        delete(currToken.get());
    }

}
