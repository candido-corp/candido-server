package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;

public class InvalidPasswordAccountException extends CustomRuntimeException {
    public InvalidPasswordAccountException(String messages, Object[] args) {
        super(messages, args);
    }
}
