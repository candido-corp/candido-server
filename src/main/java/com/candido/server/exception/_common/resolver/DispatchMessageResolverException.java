package com.candido.server.exception._common.resolver;

import com.candido.server.exception._common.CustomRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Service for resolving messages for different types of exceptions.
 */
@Service
@RequiredArgsConstructor
public class DispatchMessageResolverException extends MessageResolverException {

    private final MessageResolverAuthenticationException messageResolverAuthenticationException;
    private final MessageResolverBusinessException messageResolverBusinessException;
    private final MessageResolverValidationException messageResolverValidationException;

    /**
     * Resolves the message for the given exception based on the provided locale and type of exception.
     *
     * @param ex the exception to resolve the message for
     * @param locale the locale to use for message resolution
     * @return the resolved message as a string
     */
    @Override
    public String resolveMessage(CustomRuntimeException ex, Locale locale, EnumMessageResolverExceptionType type) {
        return switch (type) {
            case AUTHENTICATION -> messageResolverAuthenticationException.resolveMessage(ex, locale);
            case BUSINESS -> messageResolverBusinessException.resolveMessage(ex, locale);
            case VALIDATION -> messageResolverValidationException.resolveMessage(ex, locale);
        };
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
        return exs
                .stream()
                .map(exception -> resolveMessage(exception, locale, type))
                .collect(Collectors.toList());
    }

}
