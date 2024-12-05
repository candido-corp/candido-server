package com.candido.server.dto.v1.request.auth;

import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception._common.validation.CustomNotBlank;
import com.candido.server.exception._common.validation.PasswordsMatch;
import com.candido.server.exception._common.validation.ValidPassword;
import com.candido.server.exception.account.ExceptionInvalidPasswordAccount;
import com.candido.server.exception.account.ExceptionPasswordsDoNotMatch;
import com.candido.server.exception.security.auth.ExceptionValidationAuth;
import com.fasterxml.jackson.annotation.JsonProperty;

@PasswordsMatch(
        exception = ExceptionPasswordsDoNotMatch.class,
        exceptionName = EnumExceptionName.ERROR_VALIDATION_PASSWORDS_DO_NOT_MATCH
)
public record RequestPasswordReset (
        @JsonProperty(JSON_PROPERTY_UUID_ACCESS)
        @CustomNotBlank(
                exception = ExceptionValidationAuth.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_TOKEN_CAN_NOT_BE_EMPTY,
                exceptionFields = {JSON_PROPERTY_UUID_ACCESS}
        )
        String uuidAccessToken,

        @JsonProperty(JSON_PROPERTY_ENCRYPTED_EMAIL)
        @CustomNotBlank(
                exception = ExceptionValidationAuth.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_EMAIL_CAN_NOT_BE_EMPTY,
                exceptionFields = {JSON_PROPERTY_ENCRYPTED_EMAIL}
        )
        String encryptedEmail,

        @JsonProperty(JSON_PROPERTY_PASSWORD)
        @CustomNotBlank(
                exception = ExceptionInvalidPasswordAccount.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_PASSWORD_CAN_NOT_BE_EMPTY,
                exceptionFields = {JSON_PROPERTY_PASSWORD}
        )
        @ValidPassword
        String password,

        @JsonProperty(JSON_PROPERTY_CONFIRM_PASSWORD)
        @CustomNotBlank(
                exception = ExceptionInvalidPasswordAccount.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_CONFIRM_PASSWORD_CAN_NOT_BE_EMPTY,
                exceptionFields = {JSON_PROPERTY_CONFIRM_PASSWORD}
        )
        String confirmPassword
) implements PasswordConfirmation {

    public static final String JSON_PROPERTY_UUID_ACCESS = "t";
    public static final String JSON_PROPERTY_ENCRYPTED_EMAIL = "e";
    public static final String JSON_PROPERTY_PASSWORD = "password";
    public static final String JSON_PROPERTY_CONFIRM_PASSWORD = "confirm_password";

}
