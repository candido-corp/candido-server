package com.candido.server.error.account;

import com.candido.server.exception._common.BTExceptionResolver;
import com.candido.server.exception._common.BTExceptionResponse;
import com.candido.server.exception._common.BTExceptionResponseList;
import com.candido.server.exception.account.*;
import io.swagger.v3.oas.annotations.Hidden;
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

    @ExceptionHandler({DuplicateAccountException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<BTExceptionResponse> handleDuplicateAccountException(DuplicateAccountException ex, Locale locale) {
        log.info("[EXCEPTION] ({}) -> {}", DuplicateAccountException.class.getName(), LocalDateTime.now());
        return btExceptionResolver.resolveBusinessBTException(ex, locale, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({PasswordsDoNotMatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BTExceptionResponse> handlePasswordsDoNotMatchException(PasswordsDoNotMatchException ex, Locale locale) {
        log.info("[EXCEPTION] ({}) -> {}", PasswordsDoNotMatchException.class.getName(), LocalDateTime.now());
        return btExceptionResolver.resolveValidationBTException(ex, locale, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EmailsDoNotMatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BTExceptionResponse> handleEmailsDoNotMatchException(EmailsDoNotMatchException ex, Locale locale) {
        log.info("[EXCEPTION] ({}) -> {}", EmailsDoNotMatchException.class.getName(), LocalDateTime.now());
        return btExceptionResolver.resolveValidationBTException(ex, locale, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AccountNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<BTExceptionResponse> handleAccountNotFoundException(AccountNotFoundException ex, Locale locale) {
        log.info("[EXCEPTION] ({}) -> {}", AccountNotFoundException.class.getName(), LocalDateTime.now());
        return btExceptionResolver.resolveBusinessBTException(ex, locale, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidEmailAccountException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BTExceptionResponse> handleInvalidEmailAccountException(InvalidEmailAccountException ex, Locale locale) {
        log.info("[EXCEPTION] ({}) -> {}", InvalidEmailAccountException.class.getName(), LocalDateTime.now());
        return btExceptionResolver.resolveValidationBTException(ex, locale, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidPasswordAccountListException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BTExceptionResponseList> handleInvalidPasswordAccountException(InvalidPasswordAccountListException ex, Locale locale) {
        log.info("[EXCEPTION] ({}) -> {}", InvalidPasswordAccountListException.class.getName(), LocalDateTime.now());
        return btExceptionResolver.resolvePasswordValidationBTException(ex, locale, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({FirstNameEmptyException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BTExceptionResponse> handleFirstNameEmptyException(FirstNameEmptyException ex, Locale locale) {
        log.info("[EXCEPTION] ({}) -> {}", FirstNameEmptyException.class.getName(), LocalDateTime.now());
        return btExceptionResolver.resolveBusinessBTException(ex, locale, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({LastNameEmptyException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BTExceptionResponse> handleLastNameEmptyException(LastNameEmptyException ex, Locale locale) {
        log.info("[EXCEPTION] ({}) -> {}", LastNameEmptyException.class.getName(), LocalDateTime.now());
        return btExceptionResolver.resolveBusinessBTException(ex, locale, HttpStatus.BAD_REQUEST);
    }

}
