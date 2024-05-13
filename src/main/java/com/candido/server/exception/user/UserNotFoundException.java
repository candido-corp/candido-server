package com.candido.server.exception.user;

import com.candido.server.exception._common.CustomRuntimeException;

public class UserNotFoundException extends CustomRuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
