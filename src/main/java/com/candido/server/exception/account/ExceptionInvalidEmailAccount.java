package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;

import java.util.List;

public class ExceptionInvalidEmailAccount extends CustomRuntimeException {
    public ExceptionInvalidEmailAccount(String message) {
        super(message);
    }
    public ExceptionInvalidEmailAccount(String message, List<String> fields) {
        super(message, fields);
    }
}
