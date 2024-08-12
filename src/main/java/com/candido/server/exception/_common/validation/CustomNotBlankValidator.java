package com.candido.server.exception._common.validation;

import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.util.ExceptionValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.messageinterpolation.HibernateMessageInterpolatorContext;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
     * The fields to include in the exception message.
     */
    private String[] exceptionFields;

    /**
     * Initializes the validator with the annotation details.
     *
     * @param constraintAnnotation The annotation instance with its defined values.
     */
    @Override
    public void initialize(CustomNotBlank constraintAnnotation) {
        this.exceptionClass = constraintAnnotation.exception();
        this.exceptionMessage = constraintAnnotation.exceptionName().name();
        this.exceptionFields = constraintAnnotation.exceptionFields();
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
                    .getConstructor(String.class, List.class)
                    .newInstance(exceptionMessage, Arrays.asList(exceptionFields));
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ExceptionValidation(EnumExceptionName.ERROR_VALIDATION_INVALID_EXCEPTION_CONFIGURATION.name(), e.getMessage());
        }
    }
}