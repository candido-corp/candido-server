package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;

public class ExceptionAccountSettingsKeyValue extends CustomRuntimeException {
    public ExceptionAccountSettingsKeyValue(String message) {
        super(message);
    }

    public ExceptionAccountSettingsKeyValue(String message, String ...extraMessages) {
        super(message, extraMessages);
    }
}
