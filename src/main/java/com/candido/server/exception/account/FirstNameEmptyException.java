package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;

public class FirstNameEmptyException extends CustomRuntimeException {
    public FirstNameEmptyException(String message) {
        super(message);
    }
}
