package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;

public class PasswordsDoNotMatchException extends CustomRuntimeException {

    public PasswordsDoNotMatchException(String message) {
        super(message);
    }

}
