package com.candido.server.dto.v1.request.auth;

import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.security.auth.ExceptionAuth;
import com.candido.server.validation.password.PasswordConstraintValidator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record RequestPasswordReset (
        @JsonProperty("password") String password,
        @JsonProperty("confirm_password") String confirmPassword
) {

    public void checkPasswordIsNotBlank() {
        if (this.password == null)
            throw new ExceptionAuth(EnumExceptionName.PASSWORD_CAN_NOT_BE_EMPTY.name());
    }

    public void checkFields() {
        PasswordConstraintValidator.isValid(this.password);
        checkPasswordIsNotBlank();
        if(!this.password.equals(this.confirmPassword))
            throw new ExceptionAuth(EnumExceptionName.AUTH_PASSWORDS_DO_NOT_MATCH.name());
    }

}
