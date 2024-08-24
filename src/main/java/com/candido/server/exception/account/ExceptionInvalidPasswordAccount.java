package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

public class ExceptionInvalidPasswordAccount extends CustomRuntimeException {
    public ExceptionInvalidPasswordAccount(String message) {
        super(message);
    }
    public ExceptionInvalidPasswordAccount(String message, List<String> fields) {
        super(message, fields);
    }
    public ExceptionInvalidPasswordAccount(String message, Object[] args, Map<String, Object> details) {
        super(message, args, details);
    }
    public ExceptionInvalidPasswordAccount(String message, Object[] args, Map<String, Object> details, List<String> fields) {
        super(message, args, details, fields);
    }
}
