package com.candido.server.exception._common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * CustomNotBlankValidator is a class that implements the ConstraintValidator interface.
 * It validates that a given string is not null or empty.
 */
public class CustomNotBlankValidator implements ConstraintValidator<CustomNotBlank, String> {

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
    public void initialize(CustomNotBlank constraintAnnotation) {
        this.exceptionClass = constraintAnnotation.exception();
        this.exceptionMessage = constraintAnnotation.exceptionName().name();
    }

    /**
     * Validates the string by checking if it is not null or empty.
     *
     * @param value The string to validate.
     * @param context The context in which the constraint is evaluated.
     * @return true if the string is not null or empty, false otherwise.
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) throwException();
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
                    .getConstructor(String.class)
                    .newInstance(exceptionMessage);
        } catch (Exception e) {
            throw new RuntimeException("Invalid exception configuration", e);
        }
    }
}