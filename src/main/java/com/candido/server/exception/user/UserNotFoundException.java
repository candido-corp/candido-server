package com.candido.server.exception.user;

import com.candido.server.exception._common.BTException;

public class UserNotFoundException extends BTException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
