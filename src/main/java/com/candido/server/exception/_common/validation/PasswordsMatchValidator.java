package com.candido.server.exception._common.validation;

import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.security.auth.ExceptionValidationAuth;
import com.candido.server.exception.util.ExceptionValidation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        this.passwordField = constraintAnnotation.passwordField();
        this.confirmPasswordField = constraintAnnotation.confirmPasswordField();
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
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        String password;
        String confirmPassword;

        getFields(value);

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
     * Retrieves the fields to validate from the object.
     *
     * @param value The object from which to retrieve the fields.
     */
    private void getFields(Object value) {
        fields.clear();
        Arrays.stream(value.getClass().getDeclaredFields())
                .filter(field ->
                        field.isAnnotationPresent(JsonProperty.class) &&
                                (field.getName().equals(passwordField) || field.getName().equals(confirmPasswordField)))
                .forEach(field -> fields.add(field.getAnnotation(JsonProperty.class).value()));
    }

    /**
     * Retrieves the value of a field from an object.
     *
     * @param object The object from which to retrieve the field value.
     * @param fieldName The name of the field.
     * @return The value of the field.
     * @throws NoSuchFieldException If the field does not exist or cannot be accessed.
     */
    private String getFieldValue(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
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
                    .getConstructor(String.class, List.class)
                    .newInstance(exceptionMessage, fields);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ExceptionValidation(EnumExceptionName.ERROR_VALIDATION_INVALID_EXCEPTION_CONFIGURATION.name(), e.getMessage());
        }
    }
}
