package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;

public class ExceptionFirstNameEmpty extends CustomRuntimeException {
    public ExceptionFirstNameEmpty(String message) {
        super(message);
    }
}
