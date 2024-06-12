package com.candido.server.dto.v1.request.auth;

import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception._common.validation.CustomNotBlank;
import com.candido.server.exception.security.auth.ExceptionTemporaryCode;
import com.candido.server.exception.security.auth.ExceptionValidationAuth;
import com.fasterxml.jackson.annotation.JsonProperty;

public record RequestRegisterCodeVerify(
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

        @JsonProperty("c")
        @CustomNotBlank(
                exception = ExceptionTemporaryCode.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_TEMPORARY_CODE_CAN_NOT_BE_EMPTY
        )
        String temporaryCode
) {}
