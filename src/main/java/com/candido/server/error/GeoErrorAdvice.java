package com.candido.server.error;

import com.candido.server.exception._common.ApiErrorResponse;
import com.candido.server.exception._common.CustomExceptionResolver;
import com.candido.server.exception._common.CustomRuntimeException;
import com.candido.server.exception._common.resolver.EnumMessageResolverType;
import com.candido.server.exception.geo.ExceptionAddress;
import com.candido.server.exception.geo.ExceptionAddressNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

@RequiredArgsConstructor
@ControllerAdvice
public class GeoErrorAdvice {

    private final CustomExceptionResolver customExceptionResolver;

    @ExceptionHandler({
            ExceptionAddressNotFound.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiErrorResponse> handleAddressNotFoundException(CustomRuntimeException ex, Locale locale) {
        return customExceptionResolver.resolveException(
                ex,
                locale,
                HttpStatus.NOT_FOUND,
                EnumMessageResolverType.BUSINESS
        );
    }

    @ExceptionHandler({
            ExceptionAddress.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> handleAddressException(CustomRuntimeException ex, Locale locale) {
        return customExceptionResolver.resolveException(
                ex,
                locale,
                HttpStatus.NOT_FOUND,
                EnumMessageResolverType.BUSINESS
        );
    }

}
