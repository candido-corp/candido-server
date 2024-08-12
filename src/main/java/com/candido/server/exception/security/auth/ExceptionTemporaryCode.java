package com.candido.server.exception.security.auth;

import com.candido.server.exception._common.CustomRuntimeException;

import java.util.List;

public class ExceptionTemporaryCode extends CustomRuntimeException {
    public ExceptionTemporaryCode() {
        super();
    }
    public ExceptionTemporaryCode(String message) {
        super(message);
    }
    public ExceptionTemporaryCode(String message, List<String> fields) {
        super(message, fields);
    }
}