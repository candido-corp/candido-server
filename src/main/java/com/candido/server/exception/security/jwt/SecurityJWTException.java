package com.candido.server.exception.security.jwt;

import com.candido.server.exception._common.BTException;

public class SecurityJWTException extends BTException {

    public SecurityJWTException(String message) {
        super(message);
    }

    public SecurityJWTException(String message, Object[] args) {
        super(message, args);
    }

}
