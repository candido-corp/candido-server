package com.candido.server.exception._common;

import com.candido.server.exception._common.resolver.DispatchMessageResolverException;
import com.candido.server.exception._common.resolver.EnumMessageResolverExceptionType;
import com.candido.server.exception.account.ExceptionInvalidPasswordAccountList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomExceptionResolver implements ExceptionResolver {

    private final DispatchMessageResolverException dispatchMessageResolverException;

    /**
     * Logs the exception to the console.
     *
     * @param ex the Exception to log.
     */
    @Override
    public void printException(Exception ex, String... extraMessage) {
        String extraMessages = Optional.ofNullable(extraMessage)
                .map(messages -> String.join(" | ", messages))
                .orElse("");

        log.error("[EXCEPTION] ({}) -> {} {}",
                ex.getClass().getName(),
                LocalDateTime.now(),
                extraMessages
        );
    }

    /**
     * Resolves the message for a given exception based on the provided locale and type of exception.
     * It then wraps the resolved message in an ErrorResponse object and returns it within a ResponseEntity.
     *
     * @param ex         the CustomRuntimeException to resolve the message for.
     * @param locale     the Locale to use for message resolution.
     * @param httpStatus the HttpStatus to be included in the response.
     * @param type       the EnumMessageResolverExceptionType indicating the type of exception to resolve the message for.
     * @return a ResponseEntity containing an ErrorResponse with the resolved message and the provided HttpStatus.
     */
    @Override
    public ResponseEntity<ErrorResponse> resolveException(
            CustomRuntimeException ex,
            Locale locale,
            HttpStatus httpStatus,
            EnumMessageResolverExceptionType type
    ) {
        printException(ex);
        String errorMessage = dispatchMessageResolverException.resolveMessage(ex, locale, type);
        return new ResponseEntity<>(new ErrorResponse(errorMessage, httpStatus), httpStatus);
    }

    /**
     * Resolves the message for a given exception based on the provided locale and type of exception.
     * It then wraps the resolved message in an ErrorResponse object and returns it within a ResponseEntity.
     *
     * @param ex         the ExceptionInvalidPasswordAccountList to resolve the message for.
     * @param locale     the Locale to use for message resolution.
     * @param httpStatus the HttpStatus to be included in the response.
     * @param type       the EnumMessageResolverExceptionType indicating the type of exception to resolve the message for.
     * @return a ResponseEntity containing an ErrorResponse with the resolved message and the provided HttpStatus.
     */
    @Override
    public ResponseEntity<ErrorResponseList> resolveException(
            ExceptionInvalidPasswordAccountList ex,
            Locale locale,
            HttpStatus httpStatus,
            EnumMessageResolverExceptionType type
    ) {
        ex.getExceptions().forEach(this::printException);
        List<CustomRuntimeException> customRuntimeExceptions = new ArrayList<>(ex.getExceptions());
        List<String> messages = dispatchMessageResolverException.resolveMessage(customRuntimeExceptions, locale, type);
        var errorResponseList = new ErrorResponseList(messages, httpStatus);
        return new ResponseEntity<>(errorResponseList, httpStatus);
    }

}
