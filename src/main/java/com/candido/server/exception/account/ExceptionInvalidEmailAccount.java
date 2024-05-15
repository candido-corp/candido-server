package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;

public class ExceptionInvalidEmailAccount extends CustomRuntimeException {
    public ExceptionInvalidEmailAccount(String message) {
        super(message);
    }
}
