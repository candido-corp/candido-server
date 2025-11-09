package com.candido.server.exception.geo;

import com.candido.server.exception._common.CustomRuntimeException;

public class ExceptionAddressTypeNotFound extends CustomRuntimeException {
    public ExceptionAddressTypeNotFound(String message) {
        super(message);
    }
}
