package com.candido.server.service.auth.token;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.token.Token;
import com.candido.server.domain.v1.token.TokenScopeCategoryEnum;
import com.candido.server.domain.v1.token.TokenTypeEnum;

import java.util.List;
import java.util.Optional;

public interface TokenService {

    Optional<Token> findByAccessToken(String accessToken);
    Optional<Token> findByRefreshToken(String refreshToken);
    Optional<Token> findByUUIDAndTokenScopeCategoryId(String uuid, int tokenScopeCategoryId);
    Optional<Token> findByAccountIdAndTokenScopeCategoryId(int accountId, int tokenScopeCategoryId);

    Token saveUserToken(
            Account account,
            String accessToken,
            String refreshToken,
            String ipAddress,
            TokenTypeEnum tokenType,
            TokenScopeCategoryEnum tokenScopeCategoryEnum
    );

    void delete(Token token);

    List<Token> findAllValidTokenByUser(Integer accountId);

    void revokeAllAccountTokens(Account account);


    Token createRegistrationToken(Account account, String ipAddress);

    void validateToken(String registrationToken, Account account);

}
