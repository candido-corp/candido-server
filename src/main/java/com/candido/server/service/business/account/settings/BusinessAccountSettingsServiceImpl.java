package com.candido.server.service.business.account.settings;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.dto.v1.util.AccountDto;
import com.candido.server.dto.v1.util.AccountSettingsDto;
import com.candido.server.service.base.account.AccountService;
import com.candido.server.service.base.account.AccountSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessAccountSettingsServiceImpl implements BusinessAccountSettingsService {

    private final AccountSettingsService accountSettingsService;

    @Autowired
    public BusinessAccountSettingsServiceImpl(
            AccountSettingsService accountSettingsService
    ) {
        this.accountSettingsService = accountSettingsService;
    }

    @Override
    public List<AccountSettingsDto> getAccountSettings(Authentication authentication) {
        return accountSettingsService.getAllAccountSettings(
                ((Account) authentication.getPrincipal()).getId()
        ).stream().map(AccountSettingsDto::of).toList();
    }

    @Override
    public void updateAccountSettings(Authentication authentication, AccountSettingsDto accountSettingsDto) {
        Account account = (Account) authentication.getPrincipal();
        accountSettingsService.saveAccountSetting(
                account.getId(),
                accountSettingsDto.settingsKey(),
                accountSettingsDto.settingsValue()
        );
    }
}
