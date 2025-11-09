package com.candido.server.validation.account;

import com.candido.server.domain.v1.account.AccountSettingsKeyEnum;
import com.candido.server.exception.account.ExceptionInvalidAccountSettingValue;

import java.util.List;

public interface AccountSettingValidator {
    AccountSettingsKeyEnum getKey();
    List<String> getAllowedValues();
    void validate(String value) throws ExceptionInvalidAccountSettingValue;
}
