package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

public class ExceptionInvalidPasswordAccount extends CustomRuntimeException {
    public ExceptionInvalidPasswordAccount(String messages, Object[] args, Map<String, Object> details) {
        super(messages, args, details);
    }
}
