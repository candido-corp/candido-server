package com.candido.server.dto.v1.request.account;

import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception._common.validation.CustomNotBlank;
import com.candido.server.exception._common.validation.PasswordsMatch;
import com.candido.server.exception._common.validation.ValidPassword;
import com.candido.server.exception.account.ExceptionPasswordsDoNotMatch;
import com.candido.server.exception.security.auth.ExceptionValidationAuth;
import com.fasterxml.jackson.annotation.JsonProperty;

@PasswordsMatch(
        passwordField = "password",
        confirmPasswordField = "confirmPassword",
        exception = ExceptionPasswordsDoNotMatch.class,
        exceptionName = EnumExceptionName.ERROR_VALIDATION_PASSWORDS_DO_NOT_MATCH
)
public record RequestEditAccountPassword (
        @JsonProperty("current_password")
        @CustomNotBlank(
                exception = ExceptionValidationAuth.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_PASSWORD_CAN_NOT_BE_EMPTY
        )
        String currentPassword,

        @JsonProperty("password")
        @CustomNotBlank(
                exception = ExceptionValidationAuth.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_PASSWORD_CAN_NOT_BE_EMPTY
        )
        @ValidPassword
        String password,

        @JsonProperty("confirm_password")
        @CustomNotBlank(
                exception = ExceptionValidationAuth.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_PASSWORD_CAN_NOT_BE_EMPTY
        )
        String confirmPassword
) {}
