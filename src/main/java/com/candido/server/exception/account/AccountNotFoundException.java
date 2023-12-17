package com.candido.server.exception.account;

import com.candido.server.exception._common.BTException;

public class AccountNotFoundException extends BTException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
