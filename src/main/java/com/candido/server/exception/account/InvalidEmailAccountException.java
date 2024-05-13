package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;

public class InvalidEmailAccountException  extends CustomRuntimeException {
    public InvalidEmailAccountException(String message) {
        super(message);
    }
}
