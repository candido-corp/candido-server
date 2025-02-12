package com.candido.server.dto.v1.util;

import com.candido.server.domain.v1.account.XrefAccountSettings;
import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.account.ExceptionAccountSettingsKeyValue;
import com.fasterxml.jackson.annotation.JsonProperty;

public record AccountSettingsDto(
        @JsonProperty("id") int settingsId,
        @JsonProperty("key") String settingsKey,
        @JsonProperty("value") String settingsValue
) {
    public AccountSettingsDto {
        if (settingsKey == null || settingsValue == null) {
            throw new ExceptionAccountSettingsKeyValue(
                    EnumExceptionName.ERROR_VALIDATION_ACCOUNT_SETTINGS_KEY_VALUE.name()
            );
        }
    }

    public static AccountSettingsDto of(XrefAccountSettings xrefAccountSettings) {
        return new AccountSettingsDto(
                xrefAccountSettings.getAccountSettingsId(),
                xrefAccountSettings.getAccountSettings().getAccountSettingsKey(),
                xrefAccountSettings.getValue()
        );
    }
}
