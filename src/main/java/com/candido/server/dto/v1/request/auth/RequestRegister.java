package com.candido.server.dto.v1.request.auth;

import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception._common.validation.CustomNotBlank;
import com.candido.server.exception._common.validation.PasswordsMatch;
import com.candido.server.exception.account.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@PasswordsMatch(
        passwordField = "password",
        confirmPasswordField = "confirmPassword",
        exception = ExceptionPasswordsDoNotMatch.class,
        exceptionName = EnumExceptionName.ERROR_VALIDATION_PASSWORDS_DO_NOT_MATCH
)
public record RequestRegister (
        @JsonProperty(JSON_PROPERTY_EMAIL)
        @CustomNotBlank(
                exception = ExceptionInvalidEmailAccount.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_EMAIL_CAN_NOT_BE_EMPTY,
                exceptionFields = {JSON_PROPERTY_EMAIL}
        )
        String email,

        @JsonProperty(JSON_PROPERTY_PASSWORD)
        @CustomNotBlank(
                exception = ExceptionInvalidPasswordAccount.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_PASSWORD_CAN_NOT_BE_EMPTY,
                exceptionFields = {JSON_PROPERTY_PASSWORD}
        )
        String password,

        @JsonProperty(JSON_PROPERTY_CONFIRM_PASSWORD)
        @CustomNotBlank(
                exception = ExceptionInvalidPasswordAccount.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_CONFIRM_PASSWORD_CAN_NOT_BE_EMPTY,
                exceptionFields = {JSON_PROPERTY_CONFIRM_PASSWORD}
        )
        String confirmPassword,

        @JsonProperty(JSON_PROPERTY_FIRST_NAME)
        @CustomNotBlank(
                exception = ExceptionFirstNameEmpty.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_FIRST_NAME_CAN_NOT_BE_EMPTY,
                exceptionFields = {JSON_PROPERTY_FIRST_NAME}
        )
        String firstName,

        @JsonProperty(JSON_PROPERTY_LAST_NAME)
        @CustomNotBlank(
                exception = ExceptionLastNameEmpty.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_LAST_NAME_CAN_NOT_BE_EMPTY,
                exceptionFields = {JSON_PROPERTY_LAST_NAME}
        )
        String lastName
) {
    public static final String JSON_PROPERTY_EMAIL = "email";
    public static final String JSON_PROPERTY_PASSWORD = "password";
    public static final String JSON_PROPERTY_CONFIRM_PASSWORD = "confirm_password";
    public static final String JSON_PROPERTY_FIRST_NAME = "first_name";
    public static final String JSON_PROPERTY_LAST_NAME = "last_name";
}
