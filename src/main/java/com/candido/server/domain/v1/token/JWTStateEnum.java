package com.candido.server.domain.v1.token;

public enum JWTStateEnum {
    JWT_EXPIRED,
    TOKEN_NULL_EMPTY_OR_WHITESPACE,
    JWT_INVALID,
    JWT_NOT_SUPPORTED,
    SIGNATURE_VALIDATION_FAILED;
}
