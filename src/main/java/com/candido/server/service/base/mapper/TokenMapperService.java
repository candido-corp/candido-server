package com.candido.server.service.base.mapper;

import com.candido.server.domain.v1.token.Token;
import com.candido.server.dto.v1.response.auth.ResponseToken;

public interface TokenMapperService {
    ResponseToken tokenToTokenDto(Token token);
}
