package com.candido.server.service.base.account;

import com.candido.server.domain.v1.account.AccountSettings;
import com.candido.server.domain.v1.account.XrefAccountSettings;

import java.util.List;
import java.util.Optional;

public interface AccountSettingsService {
    Optional<AccountSettings> byKey(String key);
    List<XrefAccountSettings> getAllAccountSettings(Long accountId);
    <T> void saveAccountSetting(Long accountId, String key, T value);
    Optional<XrefAccountSettings> getAccountSetting(int accountId, String key);
    void deleteAccountSetting(int accountId, String key);
    void deleteAllAccountSettings(int accountId);
}
