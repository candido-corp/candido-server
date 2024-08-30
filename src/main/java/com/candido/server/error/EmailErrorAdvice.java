package com.candido.server.error;

import com.candido.server.exception._common.ApiErrorResponse;
import com.candido.server.exception._common.CustomExceptionResolver;
import com.candido.server.exception._common.CustomRuntimeException;
import com.candido.server.exception._common.resolver.EnumMessageResolverType;
import com.candido.server.exception.account.*;
import com.candido.server.exception.email.ExceptionEmail;
import com.candido.server.exception.security.auth.ExceptionTemporaryCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class EmailErrorAdvice {

    private final CustomExceptionResolver customExceptionResolver;

    @ExceptionHandler({ExceptionEmail.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiErrorResponse> handleEmailException(CustomRuntimeException ex, Locale locale) {
        return customExceptionResolver.resolveException(
                ex,
                locale,
                HttpStatus.CONFLICT,
                EnumMessageResolverType.BUSINESS
        );
    }

}
