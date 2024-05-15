package com.candido.server.error.account;

import com.candido.server.exception._common.BTExceptionResolver;
import com.candido.server.exception._common.ErrorResponse;
import com.candido.server.exception._common.ErrorResponseList;
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

    private final BTExceptionResolver btExceptionResolver;

    @ExceptionHandler({ExceptionDuplicateAccount.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleDuplicateAccountException(ExceptionDuplicateAccount ex, Locale locale) {
        log.info("[EXCEPTION] ({}) -> {}", ExceptionDuplicateAccount.class.getName(), LocalDateTime.now());
        return btExceptionResolver.resolveBusinessBTException(ex, locale, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ExceptionPasswordsDoNotMatch.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handlePasswordsDoNotMatchException(ExceptionPasswordsDoNotMatch ex, Locale locale) {
        log.info("[EXCEPTION] ({}) -> {}", ExceptionPasswordsDoNotMatch.class.getName(), LocalDateTime.now());
        return btExceptionResolver.resolveValidationBTException(ex, locale, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ExceptionEmailsDoNotMatch.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleEmailsDoNotMatchException(ExceptionEmailsDoNotMatch ex, Locale locale) {
        log.info("[EXCEPTION] ({}) -> {}", ExceptionEmailsDoNotMatch.class.getName(), LocalDateTime.now());
        return btExceptionResolver.resolveValidationBTException(ex, locale, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ExceptionAccountNotFound.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleAccountNotFoundException(ExceptionAccountNotFound ex, Locale locale) {
        log.info("[EXCEPTION] ({}) -> {}", ExceptionAccountNotFound.class.getName(), LocalDateTime.now());
        return btExceptionResolver.resolveBusinessBTException(ex, locale, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ExceptionInvalidEmailAccount.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInvalidEmailAccountException(ExceptionInvalidEmailAccount ex, Locale locale) {
        log.info("[EXCEPTION] ({}) -> {}", ExceptionInvalidEmailAccount.class.getName(), LocalDateTime.now());
        return btExceptionResolver.resolveValidationBTException(ex, locale, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ExceptionInvalidPasswordAccountList.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseList> handleInvalidPasswordAccountException(ExceptionInvalidPasswordAccountList ex, Locale locale) {
        log.info("[EXCEPTION] ({}) -> {}", ExceptionInvalidPasswordAccountList.class.getName(), LocalDateTime.now());
        return btExceptionResolver.resolvePasswordValidationBTException(ex, locale, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ExceptionFirstNameEmpty.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleFirstNameEmptyException(ExceptionFirstNameEmpty ex, Locale locale) {
        log.info("[EXCEPTION] ({}) -> {}", ExceptionFirstNameEmpty.class.getName(), LocalDateTime.now());
        return btExceptionResolver.resolveBusinessBTException(ex, locale, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ExceptionLastNameEmpty.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleLastNameEmptyException(ExceptionLastNameEmpty ex, Locale locale) {
        log.info("[EXCEPTION] ({}) -> {}", ExceptionLastNameEmpty.class.getName(), LocalDateTime.now());
        return btExceptionResolver.resolveBusinessBTException(ex, locale, HttpStatus.BAD_REQUEST);
    }

}
