package com.candido.server.exception.security.auth;

import com.candido.server.exception._common.CustomRuntimeException;

public class ExceptionAuth extends CustomRuntimeException {
    public ExceptionAuth(String message) {
        super(message);
    }
}