package com.candido.server.dto.v1.request.auth;

import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.security.auth.ExceptionAuth;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public record RequestAuthentication (
        @JsonProperty("email") String email,
        @JsonProperty("password") String password
) {

    public void checkEmailIsNotBlank() {
        if (this.email == null)
            throw new ExceptionAuth(EnumExceptionName.EMAIL_CAN_NOT_BE_EMPTY.name());
    }

    public void checkPasswordIsNotBlank() {
        if (this.password == null)
            throw new ExceptionAuth(EnumExceptionName.PASSWORD_CAN_NOT_BE_EMPTY.name());
    }

    public void checkFields() {
        checkEmailIsNotBlank();
        checkPasswordIsNotBlank();
    }

    public UsernamePasswordAuthenticationToken toUsernamePasswordAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(this.email, this.password);
    }

}
