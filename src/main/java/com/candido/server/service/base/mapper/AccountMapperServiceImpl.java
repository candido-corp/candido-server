package com.candido.server.service.base.mapper;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.account.AccountStatus;
import com.candido.server.domain.v1.account.XrefAccountSettings;
import com.candido.server.domain.v1.user.User;
import com.candido.server.dto.v1.util.AccountDto;
import com.candido.server.dto.v1.util.AccountSettingsDto;
import com.candido.server.service.base.account.AccountSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountMapperServiceImpl implements AccountMapperService {

    @Override
    public AccountDto accountToAccountDto(Account account, User user, List<XrefAccountSettings> accountSettings) {
        if (account == null) return null;
        String status = accountStatusDescription(account);
        String email = account.getEmail();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        List<AccountSettingsDto> settings = accountSettings.stream().map(AccountSettingsDto::of).toList();
        return new AccountDto(email, status, firstName, lastName, settings);
    }

    private String accountStatusDescription(Account account) {
        if (account == null) return null;
        AccountStatus status = account.getStatus();
        if (status == null) return null;
        return status.getDescription();
    }
}
