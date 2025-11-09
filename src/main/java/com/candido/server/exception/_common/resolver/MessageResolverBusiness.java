package com.candido.server.exception._common.resolver;

import com.candido.server.exception._common.CustomRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

/**
 * Service for resolving messages related to business exceptions.
 */
@Service
public class MessageResolverBusiness extends MessageResolver {

    private final MessageSource messageSource;

    /**
     * Constructs a new instance of MessageResolverBusinessException.
     *
     * @param messageSource the message source to use for resolving messages
     */
    @Autowired
    public MessageResolverBusiness(@Qualifier("businessMessageSource") MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Resolves the message for the given business exception based on the provided locale.
     *
     * @param ex the exception to resolve the message for
     * @param locale the locale to use for message resolution
     * @return the resolved message as a string
     */
    @Override
    public String resolveMessage(CustomRuntimeException ex, Locale locale) {
        return Optional.ofNullable(ex.getMessage())
                .map(message -> messageSource.getMessage(message, ex.getArgs(), locale))
                .orElse(super.resolveMessage(ex, locale));
    }
}
