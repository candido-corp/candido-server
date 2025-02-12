package com.candido.server.controller.account;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.dto.v1.util.AccountDto;
import com.candido.server.dto.v1.util.AccountSettingsDto;
import com.candido.server.service.base.account.AccountService;
import com.candido.server.service.base.account.AccountSettingsService;
import com.candido.server.validation.annotations.VerifiedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/me/settings")
public class ControllerAccountSettings {

    private final AccountService accountService;
    private final AccountSettingsService accountSettingsService;

    @Autowired
    public ControllerAccountSettings(
            AccountService accountService,
            AccountSettingsService accountSettingsService
    ) {
        this.accountService = accountService;
        this.accountSettingsService = accountSettingsService;
    }

    @VerifiedUser
    @PutMapping
    public ResponseEntity<AccountDto> updateAccountSettings(
            Authentication authentication,
            @RequestBody AccountSettingsDto accountSettingsDto
    ) {
        Account account = accountService.findAccountByEmailOrThrow(authentication.getName());
        accountSettingsService.saveAccountSetting(
                account.getId(),
                accountSettingsDto.settingsKey(),
                accountSettingsDto.settingsValue()
        );
        return ResponseEntity.noContent().build();
    }

}
