package com.candido.server.exception.user;

import com.candido.server.exception._common.CustomRuntimeException;

public class ExceptionUserNotFound extends CustomRuntimeException {
    public ExceptionUserNotFound(String message) {
        super(message);
    }
}
