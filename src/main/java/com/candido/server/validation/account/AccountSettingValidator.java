package com.candido.server.validation.account;

import com.candido.server.exception.account.ExceptionInvalidAccountSettingValue;

public interface AccountSettingValidator {
    void validate(String value) throws ExceptionInvalidAccountSettingValue;
}
