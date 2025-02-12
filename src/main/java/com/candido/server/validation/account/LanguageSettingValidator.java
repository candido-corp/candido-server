package com.candido.server.validation.account;

import com.candido.server.exception.account.ExceptionInvalidAccountSettingValue;

import java.util.Set;

public class LanguageSettingValidator implements AccountSettingValidator {
    private static final Set<String> ALLOWED_LANGUAGES = Set.of("en", "it");

    @Override
    public void validate(String value) throws ExceptionInvalidAccountSettingValue {
        if (!ALLOWED_LANGUAGES.contains(value)) {
            throw new ExceptionInvalidAccountSettingValue("Invalid language setting: " + value);
        }
    }
}
