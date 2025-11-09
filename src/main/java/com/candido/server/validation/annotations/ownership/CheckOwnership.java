package com.candido.server.validation.annotations.ownership;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckOwnership {
    String idParam();
    Class<? extends OwnershipLoader<?>> loader();
}

