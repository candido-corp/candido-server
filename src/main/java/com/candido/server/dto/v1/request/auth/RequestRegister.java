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
        @JsonProperty("email")
        @CustomNotBlank(
                exception = ExceptionInvalidEmailAccount.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_EMAIL_CAN_NOT_BE_EMPTY
        ) String email,
        @JsonProperty("password")
        @CustomNotBlank(
                exception = ExceptionInvalidPasswordAccount.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_PASSWORD_CAN_NOT_BE_EMPTY
        ) String password,
        @JsonProperty("confirm_password")
        @CustomNotBlank(
                exception = ExceptionInvalidPasswordAccount.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_CONFIRM_PASSWORD_CAN_NOT_BE_EMPTY
        ) String confirmPassword,
        @JsonProperty("first_name")
        @CustomNotBlank(
                exception = ExceptionFirstNameEmpty.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_FIRST_NAME_CAN_NOT_BE_EMPTY
        ) String firstName,
        @JsonProperty("last_name")
        @CustomNotBlank(
                exception = ExceptionLastNameEmpty.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_LAST_NAME_CAN_NOT_BE_EMPTY
        ) String lastName
) {}
