package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;

public class ExceptionAccountSettingsKeyNotFound extends CustomRuntimeException {
    public ExceptionAccountSettingsKeyNotFound(String message) {
        super(message);
    }

    public ExceptionAccountSettingsKeyNotFound(String message, String ...extraMessages) {
        super(message, extraMessages);
    }
}
