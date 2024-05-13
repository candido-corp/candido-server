package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;

public class DuplicateAccountException extends CustomRuntimeException {
    public DuplicateAccountException() {
        super();
    }

    public DuplicateAccountException(String message) {
        super(message);
    }
}
