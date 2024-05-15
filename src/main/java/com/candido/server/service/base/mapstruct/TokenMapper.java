package com.candido.server.service.base.mapstruct;

import com.candido.server.domain.v1.token.Token;
import com.candido.server.dto.v1.response.auth.ResponseToken;
import com.candido.server.dto.v1.util.AccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TokenMapper {

    TokenMapper INSTANCE = Mappers.getMapper( TokenMapper.class );

    ResponseToken tokenToTokenDto(Token token);
}