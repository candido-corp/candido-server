package com.candido.server.exception.security.auth;

import com.candido.server.exception._common.CustomRuntimeException;

import java.util.List;

public class ExceptionValidationAuth extends CustomRuntimeException {
    public ExceptionValidationAuth(String message) {
        super(message);
    }
    public ExceptionValidationAuth(String message, String... extraMessages) {
        super(message, extraMessages);
    }
    public ExceptionValidationAuth(String message, List<String> fields) {
        super(message, fields);
    }
}