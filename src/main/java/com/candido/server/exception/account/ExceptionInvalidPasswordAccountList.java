package com.candido.server.exception.account;

import com.candido.server.exception._common.CustomRuntimeException;
import lombok.Getter;

import java.util.List;

@Getter
public class ExceptionInvalidPasswordAccountList extends CustomRuntimeException {

    private final List<ExceptionInvalidPasswordAccount> exceptions;

    public ExceptionInvalidPasswordAccountList(List<ExceptionInvalidPasswordAccount> exceptions) {
        this.exceptions = exceptions;
    }

}
