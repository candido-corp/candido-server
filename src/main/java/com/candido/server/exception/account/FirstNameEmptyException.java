package com.candido.server.exception.account;

import com.candido.server.exception._common.BTException;

public class FirstNameEmptyException extends BTException {
    public FirstNameEmptyException(String message) {
        super(message);
    }
}
