package com.candido.server.service.mapstruct;

import com.biotekna.doctor.dao.mapstruct.dto.AccountDto;
import com.biotekna.doctor.security.domain.account.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper( AccountMapper.class );

    @Mapping(target = "status", source = "status.name")
    AccountDto accountToAccountDto(Account account);
}