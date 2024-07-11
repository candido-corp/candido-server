package com.candido.server.service.base.mapper;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.account.AccountStatus;
import com.candido.server.dto.v1.util.AccountDto;
import org.springframework.stereotype.Service;

@Service
public class AccountMapperServiceImpl implements AccountMapperService {
    @Override
    public AccountDto accountToAccountDto(Account account) {
        if (account == null) return null;
        String status = accountStatusDescription(account);
        String email = account.getEmail();
        return new AccountDto(email, status);
    }

    private String accountStatusDescription(Account account) {
        if (account == null) return null;
        AccountStatus status = account.getStatus();
        if (status == null) return null;
        return status.getDescription();
    }
}
