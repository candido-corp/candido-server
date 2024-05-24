package com.candido.server.error;

import com.candido.server.exception._common.ApiErrorResponse;
import com.candido.server.exception._common.CustomExceptionResolver;
import com.candido.server.exception._common.CustomRuntimeException;
import com.candido.server.exception._common.resolver.EnumMessageResolverExceptionType;
import com.candido.server.exception.account.*;
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
public class AccountErrorAdvice {

    private final CustomExceptionResolver customExceptionResolver;

    @ExceptionHandler({ExceptionDuplicateAccount.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiErrorResponse> handleDuplicateAccountException(CustomRuntimeException ex, Locale locale) {
        return customExceptionResolver.resolveException(
                ex,
                locale,
                HttpStatus.CONFLICT,
                EnumMessageResolverExceptionType.BUSINESS
        );
    }

    @ExceptionHandler({ExceptionAccountNotFound.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiErrorResponse> handleAccountNotFoundException(CustomRuntimeException ex, Locale locale) {
        return customExceptionResolver.resolveException(
                ex,
                locale,
                HttpStatus.NOT_FOUND,
                EnumMessageResolverExceptionType.BUSINESS
        );
    }

    @ExceptionHandler({
            ExceptionInvalidEmailAccount.class,
            ExceptionEmailsDoNotMatch.class,
            ExceptionPasswordsDoNotMatch.class,
            ExceptionLastNameEmpty.class,
            ExceptionFirstNameEmpty.class,
            ExceptionInvalidPasswordAccount.class,
            ExceptionTemporaryCode.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> handleAccountEmailPasswordException(CustomRuntimeException ex, Locale locale) {
        return customExceptionResolver.resolveException(
                ex,
                locale,
                HttpStatus.BAD_REQUEST,
                EnumMessageResolverExceptionType.VALIDATION
        );
    }

    @ExceptionHandler({ExceptionInvalidPasswordAccountList.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> handleInvalidPasswordAccountException(ExceptionInvalidPasswordAccountList ex, Locale locale) {
        return customExceptionResolver.resolveException(
                ex,
                locale,
                HttpStatus.BAD_REQUEST,
                EnumMessageResolverExceptionType.VALIDATION
        );
    }

}
