package com.candido.server.exception.account;

import com.candido.server.exception._common.BTException;

public class LastNameEmptyException extends BTException {
    public LastNameEmptyException(String message) {
        super(message);
    }
}
