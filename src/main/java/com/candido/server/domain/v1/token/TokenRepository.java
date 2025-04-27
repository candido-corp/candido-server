package com.candido.server.domain.v1.token;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @EntityGraph(attributePaths = {"tokenScopeCategory", "tokenType"})
    List<Token> findAllByAccountId(Integer id);

    Optional<Token> findByAccessToken(String accessToken);
    Optional<Token> findByRefreshToken(String refreshToken);
    Optional<Token> findByUuidAccessTokenAndTokenScopeCategoryId(String uuidAccessToken, int tokenScopeCategoryId);
    Optional<Token> findByAccessTokenAndTokenScopeCategoryId(String accessToken, int tokenScopeCategoryId);
    Optional<Token> findByAccountIdAndTokenScopeCategoryId(int accountId, int tokenScopeCategoryId);

}
