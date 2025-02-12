package com.candido.server.validation.account;

import com.candido.server.exception.account.ExceptionInvalidAccountSettingValue;

import java.util.Set;

public class ThemeSettingValidator implements AccountSettingValidator {
    private static final Set<String> ALLOWED_VALUES = Set.of("light", "dark", "system");

    @Override
    public void validate(String value) throws ExceptionInvalidAccountSettingValue {
        if (!ALLOWED_VALUES.contains(value)) {
            throw new ExceptionInvalidAccountSettingValue("Invalid theme setting: " + value);
        }
    }
}