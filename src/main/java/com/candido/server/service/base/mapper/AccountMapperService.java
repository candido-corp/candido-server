package com.candido.server.service.base.mapper;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.account.XrefAccountSettings;
import com.candido.server.domain.v1.user.User;
import com.candido.server.dto.v1.util.AccountDto;

import java.util.List;

public interface AccountMapperService {
    AccountDto accountToAccountDto(Account account, User user, List<XrefAccountSettings> accountSettings);
}
