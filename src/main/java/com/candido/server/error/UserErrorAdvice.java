package com.candido.server.error;

import com.candido.server.exception._common.CustomExceptionResolver;
import com.candido.server.exception._common.CustomRuntimeException;
import com.candido.server.exception._common.ApiErrorResponse;
import com.candido.server.exception._common.resolver.EnumMessageResolverType;
import com.candido.server.exception.user.ExceptionAddressNotFound;
import com.candido.server.exception.user.ExceptionUserNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

@RequiredArgsConstructor
@ControllerAdvice
public class UserErrorAdvice {

    private final CustomExceptionResolver customExceptionResolver;

    @ExceptionHandler({
            ExceptionUserNotFound.class,
            ExceptionAddressNotFound.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiErrorResponse> handleUserNotFoundException(CustomRuntimeException ex, Locale locale) {
        return customExceptionResolver.resolveException(
                ex,
                locale,
                HttpStatus.NOT_FOUND,
                EnumMessageResolverType.BUSINESS
        );
    }

}
