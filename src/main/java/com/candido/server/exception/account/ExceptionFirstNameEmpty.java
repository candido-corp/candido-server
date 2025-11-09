package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;

import java.util.List;

public class ExceptionFirstNameEmpty extends CustomRuntimeException {
    public ExceptionFirstNameEmpty(String message) {
        super(message);
    }
    public ExceptionFirstNameEmpty(String message, List<String> fields) {
        super(message, fields);
    }
}
