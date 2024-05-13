package com.candido.server.exception.security.jwt;

import com.candido.server.exception._common.CustomRuntimeException;

public class SecurityJWTException extends CustomRuntimeException {

    public SecurityJWTException(String message) {
        super(message);
    }

    public SecurityJWTException(String message, Object[] args) {
        super(message, args);
    }

}
