package com.candido.server.exception.security.jwt;

import com.candido.server.exception._common.CustomRuntimeException;

public class ExceptionSecurityJwt extends CustomRuntimeException {

    public ExceptionSecurityJwt(String message) {
        super(message);
    }

    public ExceptionSecurityJwt(String message, Object[] args) {
        super(message, args);
    }

}
