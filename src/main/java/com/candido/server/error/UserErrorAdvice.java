package com.candido.server.error;

import com.candido.server.exception._common.CustomExceptionResolver;
import com.candido.server.exception._common.CustomRuntimeException;
import com.candido.server.exception._common.ErrorResponse;
import com.candido.server.exception._common.resolver.EnumMessageResolverExceptionType;
import com.candido.server.exception.user.ExceptionUserNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class UserErrorAdvice {

    private final CustomExceptionResolver customExceptionResolver;

    @ExceptionHandler({ExceptionUserNotFound.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(CustomRuntimeException ex, Locale locale) {
        return customExceptionResolver.resolveException(
                ex,
                locale,
                HttpStatus.NOT_FOUND,
                EnumMessageResolverExceptionType.BUSINESS
        );
    }

}
