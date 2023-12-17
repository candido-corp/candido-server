package com.candido.server.exception.account;

import com.candido.server.exception._common.BTException;

public class PasswordsDoNotMatchException extends BTException {

    public PasswordsDoNotMatchException(String message) {
        super(message);
    }

}
