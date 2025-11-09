package com.candido.server.exception._common.resolver;

import com.candido.server.exception._common.CustomRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

/**
 * Service for resolving messages for different types of exceptions.
 */
@Service
@RequiredArgsConstructor
public class DispatchMessageResolver extends MessageResolver {

    private final MessageResolverAuthentication messageResolverAuthentication;
    private final MessageResolverBusiness messageResolverBusiness;
    private final MessageResolverValidation messageResolverValidation;

    /**
     * Resolves the message for the given exception based on the provided locale and type of exception.
     *
     * @param ex the exception to resolve the message for
     * @param locale the locale to use for message resolution
     * @return the resolved message as a string
     */
    @Override
    public String resolveMessage(CustomRuntimeException ex, Locale locale, EnumMessageResolverType type) {
        return switch (type) {
            case AUTHENTICATION -> messageResolverAuthentication.resolveMessage(ex, locale);
            case BUSINESS -> messageResolverBusiness.resolveMessage(ex, locale);
            case VALIDATION -> messageResolverValidation.resolveMessage(ex, locale);
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
    @Override
    public List<String> resolveMessage(List<CustomRuntimeException> exs, Locale locale, EnumMessageResolverType type) {
        return exs
                .stream()
                .map(exception -> resolveMessage(exception, locale, type))
                .toList();
    }

}
