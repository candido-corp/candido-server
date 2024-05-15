package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;

public class ExceptionEmailsDoNotMatch extends CustomRuntimeException {

    public ExceptionEmailsDoNotMatch(String message) {
        super(message);
    }

}
