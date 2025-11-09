package com.candido.server.exception.util;

import com.candido.server.exception._common.CustomRuntimeException;

import java.util.List;

public class ExceptionValidation extends CustomRuntimeException {
    public ExceptionValidation(String message) {
        super(message);
    }
    public ExceptionValidation(String message, String... extraMessages) {
        super(message, extraMessages);
    }
    public ExceptionValidation(String message, List<String> fields) {
        super(message, fields);
    }
}