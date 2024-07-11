package com.candido.server.service.base.mapper;

import com.candido.server.domain.v1.token.Token;
import com.candido.server.dto.v1.response.auth.ResponseToken;
import org.springframework.stereotype.Service;

@Service
public class TokenMapperServiceImpl implements TokenMapperService {
    @Override
    public ResponseToken tokenToTokenDto(Token token) {
        if (token == null) return null;

        ResponseToken responseToken = new ResponseToken();

        responseToken.setId(token.getId());
        responseToken.setAccount(token.getAccount());
        responseToken.setTokenType(token.getTokenType());
        responseToken.setTokenScopeCategory(token.getTokenScopeCategory());
        responseToken.setAccessToken(token.getAccessToken());
        responseToken.setAccessTokenExpiration(token.getAccessTokenExpiration());
        responseToken.setRefreshToken(token.getRefreshToken());
        responseToken.setRefreshTokenExpiration(token.getRefreshTokenExpiration());
        responseToken.setIpAddress(token.getIpAddress());
        responseToken.setUuidAccessToken(token.getUuidAccessToken());

        return responseToken;
    }
}
