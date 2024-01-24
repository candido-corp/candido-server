package com.candido.server.exception.account;

import com.candido.server.exception._common.BTException;

public class EmailsDoNotMatchException extends BTException {

    public EmailsDoNotMatchException(String message) {
        super(message);
    }

}
