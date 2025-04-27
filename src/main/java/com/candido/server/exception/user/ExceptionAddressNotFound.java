package com.candido.server.exception.user;

import com.candido.server.exception._common.CustomRuntimeException;

public class ExceptionAddressNotFound extends CustomRuntimeException {
    public ExceptionAddressNotFound(String message) {
        super(message);
    }
}
