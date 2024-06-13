package com.candido.server.exception.security.auth;

import com.candido.server.exception._common.CustomRuntimeException;

public class ExceptionInvalidToken extends CustomRuntimeException {
    public ExceptionInvalidToken(String message) {
        super(message);
    }
}