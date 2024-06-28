package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;

public class ExceptionAccountRoleNotFound extends CustomRuntimeException {
    public ExceptionAccountRoleNotFound(String message) {
        super(message);
    }
}
