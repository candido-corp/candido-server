package com.candido.server.exception.security.jwt;

import com.candido.server.exception._common.CustomRuntimeException;

public class ExceptionSecurityJWT extends CustomRuntimeException {

    public ExceptionSecurityJWT(String message) {
        super(message);
    }

    public ExceptionSecurityJWT(String message, Object[] args) {
        super(message, args);
    }

}
