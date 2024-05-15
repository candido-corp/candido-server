package com.candido.server.service.base.mapstruct;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.dto.v1.util.AccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper( AccountMapper.class );

    @Mapping(target = "status", source = "status.description")
    AccountDto accountToAccountDto(Account account);
}