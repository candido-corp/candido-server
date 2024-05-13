package com.candido.server.exception.security.auth;

import com.candido.server.exception._common.CustomRuntimeException;

public class AuthException extends CustomRuntimeException {
    public AuthException(String message) {
        super(message);
    }
}