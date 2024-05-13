package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;

public class AccountNotFoundException extends CustomRuntimeException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
