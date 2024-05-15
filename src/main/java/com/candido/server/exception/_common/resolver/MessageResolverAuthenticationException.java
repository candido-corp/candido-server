package com.candido.server.exception._common.resolver;

import com.candido.server.exception._common.CustomRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Service for resolving messages related to authentication exceptions.
 */
@Service
public class MessageResolverAuthenticationException implements ExceptionMessageResolver {

    private final MessageSource messageSource;

    /**
     * Constructs a new instance of MessageResolverAuthenticationException.
     *
     * @param messageSource the message source to use for resolving messages
     */
    @Autowired
    public MessageResolverAuthenticationException(@Qualifier("authenticationMessageSource") MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Resolves the message for the given authentication exception based on the provided locale.
     *
     * @param ex the exception to resolve the message for
     * @param locale the locale to use for message resolution
     * @return the resolved message as a string
     */
    @Override
    public String resolveMessage(CustomRuntimeException ex, Locale locale) {
        return messageSource.getMessage(ex.getMessage(), ex.getArgs(), locale);
    }
}
