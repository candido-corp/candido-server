package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;

import java.util.List;

public class ExceptionLastNameEmpty extends CustomRuntimeException {
    public ExceptionLastNameEmpty(String message) {
        super(message);
    }
    public ExceptionLastNameEmpty(String message, List<String> fields) {
        super(message, fields);
    }
}
