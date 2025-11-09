package com.candido.server.exception.security.auth;

import com.candido.server.exception._common.CustomRuntimeException;

public class ExceptionForbidden extends CustomRuntimeException {
    public ExceptionForbidden(String message) {
        super(message);
    }
}
