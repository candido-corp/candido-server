package com.candido.server.dto.v1.request.auth;

import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception._common.validation.CustomNotBlank;
import com.candido.server.exception.security.auth.ExceptionValidationAuth;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


public record RequestAuthentication (
        @JsonProperty(JSON_PROPERTY_EMAIL)
        @CustomNotBlank(
                exception = ExceptionValidationAuth.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_EMAIL_CAN_NOT_BE_EMPTY,
                exceptionFields = {JSON_PROPERTY_EMAIL}
        )
        String email,

        @JsonProperty(JSON_PROPERTY_PASSWORD)
        @CustomNotBlank(
                exception = ExceptionValidationAuth.class,
                exceptionName = EnumExceptionName.ERROR_VALIDATION_PASSWORD_CAN_NOT_BE_EMPTY,
                exceptionFields = {JSON_PROPERTY_PASSWORD}
        )
        String password
) {

    public static final String JSON_PROPERTY_EMAIL = "email";
    public static final String JSON_PROPERTY_PASSWORD = "password";

    /**
     * Convert the request to a UsernamePasswordAuthenticationToken.
     *
     * @return UsernamePasswordAuthenticationToken
     */
    public UsernamePasswordAuthenticationToken toUsernamePasswordAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(this.email, this.password);
    }

}
