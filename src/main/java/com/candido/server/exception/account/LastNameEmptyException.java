package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;

public class LastNameEmptyException extends CustomRuntimeException {
    public LastNameEmptyException(String message) {
        super(message);
    }
}
