package com.candido.server.exception._common.validation;

import com.candido.server.exception._common.EnumExceptionName;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CustomNotBlankValidator.class)
@Target({
        ElementType.METHOD,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER,
        ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomNotBlank {
    String message() default "Field cannot be blank";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends RuntimeException> exception();
    EnumExceptionName exceptionName();
}
