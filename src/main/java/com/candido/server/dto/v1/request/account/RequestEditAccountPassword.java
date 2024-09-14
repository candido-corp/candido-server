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
public record RequestEditAccountPassword(
        @JsonProperty(JSON_PROPERTY_CURRENT_PASSWORD)
        @CustomNotBlank(
                exception = ExceptionValidationAuth.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_PASSWORD_CAN_NOT_BE_EMPTY,
                exceptionFields = {JSON_PROPERTY_CURRENT_PASSWORD}
        )
        String currentPassword,

        @JsonProperty(JSON_PROPERTY_PASSWORD)
        @CustomNotBlank(
                exception = ExceptionValidationAuth.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_PASSWORD_CAN_NOT_BE_EMPTY,
                exceptionFields = {JSON_PROPERTY_PASSWORD}
        )
        @ValidPassword
        String password,

        @JsonProperty(JSON_PROPERTY_CONFIRM_PASSWORD)
        @CustomNotBlank(
                exception = ExceptionValidationAuth.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_PASSWORD_CAN_NOT_BE_EMPTY,
                exceptionFields = {JSON_PROPERTY_CONFIRM_PASSWORD}
        )
        String confirmPassword
) {
    public static final String JSON_PROPERTY_CURRENT_PASSWORD = "current_password";
    public static final String JSON_PROPERTY_PASSWORD = "password";
    public static final String JSON_PROPERTY_CONFIRM_PASSWORD = "confirm_password";
}
