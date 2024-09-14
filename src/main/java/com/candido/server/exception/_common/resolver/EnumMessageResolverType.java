package com.candido.server.exception._common.resolver;

import lombok.Getter;

/**
 * Enum for the different types of exceptions that can be resolved.
 */
@Getter
public enum EnumMessageResolverType {
    AUTHENTICATION,
    BUSINESS,
    VALIDATION;
}
