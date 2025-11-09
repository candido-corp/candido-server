package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;

public class ExceptionAccountDisabled extends CustomRuntimeException {
    public ExceptionAccountDisabled(String message) {
        super(message);
    }
}
