package com.candido.server.dto.v1.request.auth;

import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception._common.validation.CustomNotBlank;
import com.candido.server.exception._common.validation.PasswordsMatch;
import com.candido.server.exception._common.validation.ValidPassword;
import com.candido.server.exception.account.ExceptionInvalidPasswordAccount;
import com.candido.server.exception.account.ExceptionPasswordsDoNotMatch;
import com.candido.server.exception.security.auth.ExceptionAuth;
import com.candido.server.exception.security.auth.ExceptionValidationAuth;
import com.fasterxml.jackson.annotation.JsonProperty;

@PasswordsMatch(
        passwordField = "password",
        confirmPasswordField = "confirmPassword",
        exception = ExceptionPasswordsDoNotMatch.class,
        exceptionName = EnumExceptionName.ERROR_VALIDATION_PASSWORDS_DO_NOT_MATCH
)
public record RequestPasswordReset (
        @JsonProperty("t")
        @CustomNotBlank(
                exception = ExceptionValidationAuth.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_TOKEN_CAN_NOT_BE_EMPTY
        )
        String uuidAccessToken,

        @JsonProperty("e")
        @CustomNotBlank(
                exception = ExceptionValidationAuth.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_EMAIL_CAN_NOT_BE_EMPTY
        )
        String encryptedEmail,

        @JsonProperty("password")
        @CustomNotBlank(
                exception = ExceptionInvalidPasswordAccount.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_PASSWORD_CAN_NOT_BE_EMPTY
        )
        @ValidPassword
        String password,

        @JsonProperty("confirm_password")
        @CustomNotBlank(
                exception = ExceptionInvalidPasswordAccount.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_CONFIRM_PASSWORD_CAN_NOT_BE_EMPTY
        )
        String confirmPassword
) {}
