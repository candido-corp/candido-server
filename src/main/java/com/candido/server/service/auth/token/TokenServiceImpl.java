package com.candido.server.service.auth.token;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.token.*;
import com.candido.server.security.config.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    JwtService jwtService;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    TemporaryCodeService temporaryCodeService;

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

    public Token saveUserToken(
            Account account,
            String accessToken,
            String refreshToken,
            String ipAddress,
            TokenTypeEnum tokenType,
            TokenScopeCategoryEnum tokenScopeCategoryEnum
    ) {
        // Prima di crearlo elimino tutti i token
        revokeAllAccountTokens(account);

        // Estraggo la data di scadenza
        Date accessTokenExpirationDate = jwtService.extractExpiration(accessToken);
        LocalDateTime accessTokenExpiration = Instant.ofEpochMilli(accessTokenExpirationDate.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        // Creo il token per l'utente
        var token = Token
                .builder()
                .account(account)
                .accessToken(accessToken)
                .tokenType(new TokenType(tokenType.getTokenTypeId()))
                .accessTokenExpiration(accessTokenExpiration)
                .tokenScopeCategory(new TokenScopeCategory(tokenScopeCategoryEnum.getTokenScopeCategoryId()))
                .ipAddress(ipAddress)
                .build();

        // Imposto un UUID nel caso della registrazione
        if(tokenScopeCategoryEnum.getTokenScopeCategoryId() == TokenScopeCategoryEnum.BTD_REGISTRATION.getTokenScopeCategoryId())
            token.setUuidAccessToken(UUID.randomUUID().toString().replaceAll("-", ""));

        // Imposto il refresh token se non nullo
        if(refreshToken != null) {
            Date refreshTokenExpirationDate = jwtService.extractExpiration(refreshToken);
            LocalDateTime refreshTokenExpiration = Instant.ofEpochMilli(refreshTokenExpirationDate.getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            token.setRefreshToken(refreshToken);
            token.setRefreshTokenExpiration(refreshTokenExpiration);
        }

        // Salvo il token per l'utente
        return tokenRepository.save(token);
    }

    @Override
    public void delete(Token token) {
        // Elimino le dipendenze del token prima di eliminarlo
        temporaryCodeService.findByTokenId(token.getId()).ifPresent(temporaryCode -> temporaryCodeService.delete(temporaryCode));

        tokenRepository.delete(token);
    }

    @Override
    public List<Token> findAllValidTokenByUser(Integer accountId) {
        return tokenRepository.findAllByAccountId(accountId);
    }

    @Override
    public void revokeAllAccountTokens(Account account) {
        // Recupero tutti i token validi dell'account
        var validAccountTokens = findAllValidTokenByUser(account.getId());

        // Se non ce ne sono esco dalla funzione
        if(validAccountTokens.isEmpty()) return;

        // Revoco ogni token dell'utente
        validAccountTokens.forEach(this::delete);
    }

    @Override
    public Token createRegistrationToken(Account account, String ipAddress) {
        // Creo un token per la verifica della registrazione
        var accessToken = jwtService.generateRegistrationToken(account);

        // Salva il token dell'utente
        return saveUserToken(
                account,
                accessToken,
                null,
                ipAddress,
                TokenTypeEnum.BEARER,
                TokenScopeCategoryEnum.BTD_REGISTRATION
        );
    }

}
