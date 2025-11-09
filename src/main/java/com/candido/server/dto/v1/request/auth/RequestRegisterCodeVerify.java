package com.candido.server.dto.v1.request.auth;

import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception._common.validation.CustomNotBlank;
import com.candido.server.exception.security.auth.ExceptionTemporaryCode;
import com.candido.server.exception.security.auth.ExceptionValidationAuth;
import com.fasterxml.jackson.annotation.JsonProperty;

public record RequestRegisterCodeVerify(
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

        @JsonProperty(JSON_PROPERTY_TEMPORARY_CODE)
        @CustomNotBlank(
                exception = ExceptionTemporaryCode.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_TEMPORARY_CODE_CAN_NOT_BE_EMPTY,
                exceptionFields = {JSON_PROPERTY_TEMPORARY_CODE}
        )
        String temporaryCode
) {
    public static final String JSON_PROPERTY_UUID_ACCESS = "t";
    public static final String JSON_PROPERTY_ENCRYPTED_EMAIL = "e";
    public static final String JSON_PROPERTY_TEMPORARY_CODE = "c";
}
