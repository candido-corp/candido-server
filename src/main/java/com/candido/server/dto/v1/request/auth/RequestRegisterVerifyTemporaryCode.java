package com.candido.server.dto.v1.request.auth;

import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception._common.validation.CustomNotBlank;
import com.candido.server.exception.security.auth.ExceptionTemporaryCode;
import com.fasterxml.jackson.annotation.JsonProperty;

public record RequestRegisterVerifyTemporaryCode(
        @JsonProperty("temporary_code")
        @CustomNotBlank(
                exception = ExceptionTemporaryCode.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_TEMPORARY_CODE_CAN_NOT_BE_EMPTY
        ) String temporaryCode
) {}
