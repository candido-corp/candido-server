package com.candido.server.service.business.account.settings;

import com.candido.server.dto.v1.util.AccountDto;
import com.candido.server.dto.v1.util.AccountSettingsDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface BusinessAccountSettingsService {
    List<AccountSettingsDto> getAccountSettings(Authentication authentication);
    void updateAccountSettings(Authentication authentication, AccountSettingsDto accountSettingsDto);
}
