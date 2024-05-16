package com.candido.server.exception._common.resolver;

import com.candido.server.exception._common.CustomRuntimeException;

import java.util.List;
import java.util.Locale;

/**
 * Interface for resolving messages for custom runtime exceptions.
 */
public abstract class MessageResolverException {
    /**
     * Resolves the message for the given exception based on the provided locale.
     *
     * @param ex the exception to resolve the message for
     * @param locale the locale to use for message resolution
     * @return the resolved message as a string
     */
    public String resolveMessage(CustomRuntimeException ex, Locale locale) {
        return null;
    }

    /**
     * Resolves the message for the given exception based on the provided locale and type of exception.
     *
     * @param ex the exception to resolve the message for
     * @param locale the locale to use for message resolution
     * @param type the type of exception to resolve the message for
     * @return the resolved message as a string
     */
    public String resolveMessage(CustomRuntimeException ex, Locale locale, EnumMessageResolverExceptionType type) {
        return null;
    }

    /**
     * Resolves the message for the given exceptions based on the provided locale and type of exception.
     *
     * @param exs the exceptions to resolve the message for
     * @param locale the locale to use for message resolution
     * @param type the type of exception to resolve the message for
     * @return the resolved message as a string
     */
    public List<String> resolveMessage(List<CustomRuntimeException> exs, Locale locale, EnumMessageResolverExceptionType type) {
        return null;
    }
}
