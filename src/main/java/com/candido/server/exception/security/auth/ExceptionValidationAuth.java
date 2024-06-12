package com.candido.server.exception.security.auth;

import com.candido.server.exception._common.CustomRuntimeException;

public class ExceptionValidationAuth extends CustomRuntimeException {
    public ExceptionValidationAuth(String message) {
        super(message);
    }
}