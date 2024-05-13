package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;

public class EmailsDoNotMatchException extends CustomRuntimeException {

    public EmailsDoNotMatchException(String message) {
        super(message);
    }

}
