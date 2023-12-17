package com.candido.server.exception.account;

import com.candido.server.exception._common.BTException;

public class DuplicateAccountException extends BTException {
    public DuplicateAccountException() {
        super();
    }

    public DuplicateAccountException(String message) {
        super(message);
    }
}
