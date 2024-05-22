package com.candido.server.dto.v1.request.auth;

import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.account.*;
import com.candido.server.exception.security.auth.ExceptionAuth;
import com.candido.server.validation.password.PasswordConstraintValidator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestRegister (
        @JsonProperty("email") String email,
        @JsonProperty("password") String password,
        @JsonProperty("confirm_password") String confirmPassword,
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName
) {

    /**
     * Check all the fields of the request.
     */
    public void checkFields() {
        checkEmailIsNotBlank();
        checkFirstNameIsNotBlank();
        checkLastNameIsNotBlank();
        checkPasswordIsNotBlank();
        checkPasswordsMatch();
    }

    /**
     * Check if the email is not blank.
     */
    public void checkEmailIsNotBlank() {
        if (this.email == null)
            throw new ExceptionInvalidEmailAccount(EnumExceptionName.EMAIL_CAN_NOT_BE_EMPTY.name());
    }

    /**
     * Check if the password is not blank.
     */
    public void checkPasswordIsNotBlank() {
        if (this.password == null)
            throw new ExceptionInvalidPasswordAccount(EnumExceptionName.PASSWORD_CAN_NOT_BE_EMPTY.name());
    }

    /**
     * Check if the first name is not blank.
     */
    public void checkFirstNameIsNotBlank() {
        if (this.firstName == null)
            throw new ExceptionFirstNameEmpty(EnumExceptionName.FIRST_NAME_CAN_NOT_BE_EMPTY.name());
    }

    /**
     * Check if the last name is not blank.
     */
    public void checkLastNameIsNotBlank() {
        if (this.lastName == null)
            throw new ExceptionLastNameEmpty(EnumExceptionName.LAST_NAME_CAN_NOT_BE_EMPTY.name());
    }

    /**
     * Check if the password and the confirm password are the same.
     */
    public void checkPasswordsMatch() {
        if (!this.password.equals(this.confirmPassword))
            throw new ExceptionPasswordsDoNotMatch(EnumExceptionName.AUTH_PASSWORDS_DO_NOT_MATCH.name());
    }
}
