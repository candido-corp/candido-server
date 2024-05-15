package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;

public class ExceptionPasswordsDoNotMatch extends CustomRuntimeException {

    public ExceptionPasswordsDoNotMatch(String message) {
        super(message);
    }

}
