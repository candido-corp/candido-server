package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;

import java.util.List;

public class ExceptionPasswordsDoNotMatch extends CustomRuntimeException {
    public ExceptionPasswordsDoNotMatch(String message) {
        super(message);
    }
    public ExceptionPasswordsDoNotMatch(String message, List<String> fields) {
        super(message, fields);
    }
}
