package com.candido.server.exception._common.validation;

import com.candido.server.dto.v1.request.auth.RequestPasswordReset;
import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.util.ExceptionValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Validator for checking if two password fields match.
 * Implements the ConstraintValidator interface.
 */
public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, RequestPasswordReset> {

    /**
     * The exception class to be thrown when validation fails.
     */
    private Class<? extends RuntimeException> exceptionClass;

    /**
     * The message for the exception to be thrown when validation fails.
     */
    private String exceptionMessage;

    /**
     * The fields to validate.
     */
    private List<String> fields;

    /**
     * Initializes the validator with the annotation details.
     *
     * @param constraintAnnotation The annotation instance with its defined values.
     */
    @Override
    public void initialize(PasswordsMatch constraintAnnotation) {
        this.exceptionClass = constraintAnnotation.exception();
        this.exceptionMessage = constraintAnnotation.exceptionName().name();
        this.fields = new ArrayList<>();
    }

    /**
     * Validates the object by checking if the password and confirm password fields match.
     *
     * @param value The object to validate.
     * @param context The context in which the constraint is evaluated.
     * @return true if the fields match, false otherwise.
     */
    @Override
    public boolean isValid(RequestPasswordReset value, ConstraintValidatorContext context) {
        String password = value.password();
        String confirmPassword = value.confirmPassword();
        if(password != null && !password.equals(confirmPassword)) throwException();
        return true;
    }

    /**
     * Throws the configured exception.
     *
     * @throws RuntimeException If the exception configuration is invalid.
     */
    private void throwException() {
        try {
            throw exceptionClass
                    .getConstructor(String.class, List.class)
                    .newInstance(exceptionMessage, fields);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ExceptionValidation(EnumExceptionName.ERROR_VALIDATION_INVALID_EXCEPTION_CONFIGURATION.name(), e.getMessage());
        }
    }
}
