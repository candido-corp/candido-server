package com.candido.server.exception.account;

import lombok.Getter;

import java.util.List;

public class ExceptionInvalidPasswordAccountList extends RuntimeException {

    @Getter
    private final List<ExceptionInvalidPasswordAccount> exceptions;

    public ExceptionInvalidPasswordAccountList(List<ExceptionInvalidPasswordAccount> exceptions) {
        this.exceptions = exceptions;
    }

}
