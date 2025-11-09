package com.candido.server.dto.v1.response.auth;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.token.TokenScopeCategory;
import com.candido.server.domain.v1.token.TokenType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseToken {

    private Long id;

    private Account account;

    private TokenType tokenType;

    private TokenScopeCategory tokenScopeCategory;

    private String accessToken;

    private LocalDateTime accessTokenExpiration;

    private String refreshToken;

    private LocalDateTime refreshTokenExpiration;

    private String ipAddress;

    private String uuidAccessToken;

}
