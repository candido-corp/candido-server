package com.candido.server.service.auth.token;

import com.biotekna.doctor.security.domain.account.Account;
import com.biotekna.doctor.security.domain.token.Token;
import com.biotekna.doctor.security.domain.token.TokenScopeCategoryEnum;
import com.biotekna.doctor.security.domain.token.TokenTypeEnum;

import java.util.List;
import java.util.Optional;

public interface TokenService {

    Optional<Token> findByAccessToken(String accessToken);
    Optional<Token> findByRefreshToken(String refreshToken);

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

}
