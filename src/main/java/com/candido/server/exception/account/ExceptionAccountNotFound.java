package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;

public class ExceptionAccountNotFound extends CustomRuntimeException {
    public ExceptionAccountNotFound(String message) {
        super(message);
    }

    public ExceptionAccountNotFound(String ...message) {
        super(message);
    }
}
