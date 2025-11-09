package com.candido.server.validation.account.settings;

import com.candido.server.domain.v1.account.AccountSettingsKeyEnum;
import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.account.ExceptionInvalidAccountSettingValue;
import com.candido.server.validation.account.AccountSettingValidator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class LanguageSettingValidator implements AccountSettingValidator {
    private static final List<String> ALLOWED = List.of("en", "it");

    @Override
    public AccountSettingsKeyEnum getKey() {
        return AccountSettingsKeyEnum.S_LANG;
    }

    @Override
    public List<String> getAllowedValues() {
        return ALLOWED;
    }

    @Override
    public void validate(String value) throws ExceptionInvalidAccountSettingValue {
        if (!getAllowedValues().contains(value)) {
            throw new ExceptionInvalidAccountSettingValue(
                    EnumExceptionName.ERROR_VALIDATION_INVALID_SETTINGS_LANGUAGE.name(), new Object[] { value }
            );
        }
    }
}
