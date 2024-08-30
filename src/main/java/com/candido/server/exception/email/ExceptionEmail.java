package com.candido.server.exception.email;

import com.candido.server.exception._common.CustomRuntimeException;

public class ExceptionEmail extends CustomRuntimeException {
    public ExceptionEmail(String message) {
        super(message);
    }
}
