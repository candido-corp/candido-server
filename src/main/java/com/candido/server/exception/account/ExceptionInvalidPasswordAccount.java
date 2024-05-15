package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;

public class ExceptionInvalidPasswordAccount extends CustomRuntimeException {
    public ExceptionInvalidPasswordAccount(String messages, Object[] args) {
        super(messages, args);
    }
}
