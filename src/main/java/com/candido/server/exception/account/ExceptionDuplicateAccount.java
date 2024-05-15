package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;

public class ExceptionDuplicateAccount extends CustomRuntimeException {
    public ExceptionDuplicateAccount() {
        super();
    }

    public ExceptionDuplicateAccount(String message) {
        super(message);
    }
}
