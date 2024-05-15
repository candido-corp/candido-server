package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;

public class ExceptionLastNameEmpty extends CustomRuntimeException {
    public ExceptionLastNameEmpty(String message) {
        super(message);
    }
}
