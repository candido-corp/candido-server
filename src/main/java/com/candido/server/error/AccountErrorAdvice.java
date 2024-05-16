package com.candido.server.error;

import com.candido.server.exception._common.CustomExceptionResolver;
import com.candido.server.exception._common.CustomRuntimeException;
import com.candido.server.exception._common.ErrorResponse;
import com.candido.server.exception._common.ErrorResponseList;
import com.candido.server.exception._common.resolver.EnumMessageResolverExceptionType;
import com.candido.server.exception.account.*;
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
public class AccountErrorAdvice {

    private final CustomExceptionResolver customExceptionResolver;

    @ExceptionHandler({ExceptionDuplicateAccount.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleDuplicateAccountException(CustomRuntimeException ex, Locale locale) {
        return customExceptionResolver.resolveException(
                ex,
                locale,
                HttpStatus.CONFLICT,
                EnumMessageResolverExceptionType.BUSINESS
        );
    }

    @ExceptionHandler({ExceptionAccountNotFound.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleAccountNotFoundException(CustomRuntimeException ex, Locale locale) {
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
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleAccountEmailPasswordException(CustomRuntimeException ex, Locale locale) {
        return customExceptionResolver.resolveException(
                ex,
                locale,
                HttpStatus.BAD_REQUEST,
                EnumMessageResolverExceptionType.VALIDATION
        );
    }

    @ExceptionHandler({ExceptionInvalidPasswordAccountList.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseList> handleInvalidPasswordAccountException(ExceptionInvalidPasswordAccountList ex, Locale locale) {
        return customExceptionResolver.resolveException(
                ex,
                locale,
                HttpStatus.BAD_REQUEST,
                EnumMessageResolverExceptionType.VALIDATION
        );
    }

}
