package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;

public class ExceptionInvalidAccountSettingValue extends CustomRuntimeException {
    public ExceptionInvalidAccountSettingValue(String message) {
        super(message);
    }

    public ExceptionInvalidAccountSettingValue(String message, Object[] args) {
        super(message, args);
    }
}