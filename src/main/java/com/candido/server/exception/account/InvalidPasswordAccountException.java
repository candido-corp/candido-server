package com.candido.server.exception.account;

import com.candido.server.exception._common.BTException;

public class InvalidPasswordAccountException extends BTException {
    public InvalidPasswordAccountException(String messages, Object[] args) {
        super(messages, args);
    }
}
