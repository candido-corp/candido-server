package com.candido.server.exception._common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

/**
 * Validator for checking if two password fields match.
 * Implements the ConstraintValidator interface.
 */
public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, Object> {

    // Field names for password and confirm password
    private String passwordField;
    private String confirmPasswordField;

    /**
     * The exception class to be thrown when validation fails.
     */
    private Class<? extends RuntimeException> exceptionClass;

    /**
     * The message for the exception to be thrown when validation fails.
     */
    private String exceptionMessage;

    /**
     * Initializes the validator with the annotation details.
     *
     * @param constraintAnnotation The annotation instance with its defined values.
     */
    @Override
    public void initialize(PasswordsMatch constraintAnnotation) {
        this.passwordField = constraintAnnotation.passwordField();
        this.confirmPasswordField = constraintAnnotation.confirmPasswordField();
        this.exceptionClass = constraintAnnotation.exception();
        this.exceptionMessage = constraintAnnotation.exceptionName().name();
    }

    /**
     * Validates the object by checking if the password and confirm password fields match.
     *
     * @param value The object to validate.
     * @param context The context in which the constraint is evaluated.
     * @return true if the fields match, false otherwise.
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        String password = null;
        String confirmPassword = null;

        try {
            password = getFieldValue(value, passwordField);
            confirmPassword = getFieldValue(value, confirmPasswordField);
        } catch (Exception e) {
            return false;
        }

        if(password != null && !password.equals(confirmPassword)) throwException();
        return true;
    }

    /**
     * Retrieves the value of a field from an object.
     *
     * @param object The object from which to retrieve the field value.
     * @param fieldName The name of the field.
     * @return The value of the field.
     * @throws Exception If the field does not exist or cannot be accessed.
     */
    private String getFieldValue(Object object, String fieldName) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (String) field.get(object);
    }

    /**
     * Throws the configured exception.
     *
     * @throws RuntimeException If the exception configuration is invalid.
     */
    private void throwException() {
        try {
            throw exceptionClass
                    .getConstructor(String.class)
                    .newInstance(exceptionMessage);
        } catch (Exception e) {
            throw new RuntimeException("Invalid exception configuration", e);
        }
    }
}
