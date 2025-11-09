package com.candido.server.exception._common.validation;

import com.candido.server.validation.password.PasswordConstraintValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * ValidPasswordValidator is a class that implements the ConstraintValidator interface.
 * It validates that a given password meets certain criteria.
 */
public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    /**
     * Initializes the validator with the annotation details.
     *
     * @param constraintAnnotation The annotation instance with its defined values.
     */
    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        // No initialization needed in this case
    }

    /**
     * Validates the password by checking if it meets the defined criteria.
     *
     * @param password The password to validate.
     * @param context The context in which the constraint is evaluated.
     * @return true if the password meets the criteria, false otherwise.
     */
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) return false;
        return PasswordConstraintValidator.isValid(password);
    }
}