package com.candido.server.exception._common;

import com.candido.server.exception._common.resolver.DispatchMessageResolver;
import com.candido.server.exception._common.resolver.EnumMessageResolverType;
import com.candido.server.exception.account.ExceptionInvalidPasswordAccountList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomExceptionResolver implements ExceptionResolver {

    private final DispatchMessageResolver dispatchMessageResolver;

    /**
     * Logs the exception to the console.
     *
     * @param ex the Exception to log.
     * @param extraMessage additional messages to log.
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
     * Logs the exception to the console.
     *
     * @param ex the CustomRuntimeException to log.
     */
    @Override
    public void printException(CustomRuntimeException ex) {
        printException(ex, ex.getExtraMessages());
    }

    /**
     * Creates an ApiError object from the provided CustomRuntimeException and locale.
     *
     * @param ex    the CustomRuntimeException to create the ApiError from.
     * @param locale the Locale to use for message resolution.
     * @param type the EnumMessageResolverExceptionType indicating the type of exception to resolve the message for.
     * @return an ApiError object created from the provided CustomRuntimeException.
     */
    public ApiError createApiErrorResponse(
            CustomRuntimeException ex,
            Locale locale,
            EnumMessageResolverType type
    ) {
        String errorMessage = dispatchMessageResolver.resolveMessage(ex, locale, type);
        return ApiError
                .builder()
                .fields(ex.getFields())
                .code(ex.getMessage())
                .data(ex.getDetails())
                .message(errorMessage)
                .build();
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
    public ResponseEntity<ApiErrorResponse> resolveException(
            CustomRuntimeException ex,
            Locale locale,
            HttpStatus httpStatus,
            EnumMessageResolverType type
    ) {
        printException(ex);
        var apiError = createApiErrorResponse(ex, locale, type);
        return new ResponseEntity<>(new ApiErrorResponse(httpStatus, List.of(apiError)), httpStatus);
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
    public ResponseEntity<ApiErrorResponse> resolveException(
            ExceptionInvalidPasswordAccountList ex,
            Locale locale,
            HttpStatus httpStatus,
            EnumMessageResolverType type
    ) {
        ex.getExceptions().forEach(this::printException);
        List<CustomRuntimeException> customRuntimeExceptions = new ArrayList<>(ex.getExceptions());
        List<ApiError> apiErrorList = new ArrayList<>();
        customRuntimeExceptions.forEach(cre -> apiErrorList.add(createApiErrorResponse(cre, locale, type)));
        return new ResponseEntity<>(new ApiErrorResponse(httpStatus, apiErrorList), httpStatus);
    }

}
