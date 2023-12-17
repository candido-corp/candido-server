package com.candido.server.exception.security.auth;

import com.candido.server.exception._common.BTException;

public class AuthException extends BTException {
    public AuthException(String message) {
        super(message);
    }
}