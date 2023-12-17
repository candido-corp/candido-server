package com.candido.server.exception.account;

import com.candido.server.exception._common.BTException;

public class InvalidEmailAccountException  extends BTException {
    public InvalidEmailAccountException(String message) {
        super(message);
    }
}
