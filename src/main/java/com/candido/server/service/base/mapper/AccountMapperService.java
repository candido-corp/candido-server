package com.candido.server.service.base.mapper;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.user.User;
import com.candido.server.dto.v1.util.AccountDto;

public interface AccountMapperService {
    AccountDto accountToAccountDto(Account account, User user);
}
