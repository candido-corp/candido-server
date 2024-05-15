package com.candido.server.exception._common.resolver;

import com.candido.server.exception._common.CustomRuntimeException;

import java.util.Locale;

/**
 * Interface for resolving messages for custom runtime exceptions.
 */
public interface ExceptionMessageResolver {
    /**
     * Resolves the message for the given exception based on the provided locale.
     *
     * @param ex the exception to resolve the message for
     * @param locale the locale to use for message resolution
     * @return the resolved message as a string
     */
    String resolveMessage(CustomRuntimeException ex, Locale locale);
}
