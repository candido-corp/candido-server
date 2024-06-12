package com.candido.server.dto.v1.request.auth;

import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception._common.validation.CustomNotBlank;
import com.candido.server.exception.account.ExceptionInvalidEmailAccount;
import com.fasterxml.jackson.annotation.JsonProperty;

public record RequestPasswordResetEmail (
        @JsonProperty("email")
        @CustomNotBlank(
                exception = ExceptionInvalidEmailAccount.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_EMAIL_CAN_NOT_BE_EMPTY
        )
        String email
) {}
