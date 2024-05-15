package com.candido.server.error.user;

import com.candido.server.exception._common.BTExceptionResolver;
import com.candido.server.exception._common.ErrorResponse;
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

    private final BTExceptionResolver btExceptionResolver;

    @ExceptionHandler({ExceptionUserNotFound.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(ExceptionUserNotFound ex, Locale locale) {
        log.info("[EXCEPTION] ({}) -> {}", ExceptionUserNotFound.class.getName(), LocalDateTime.now());
        return btExceptionResolver.resolveBusinessBTException(ex, locale, HttpStatus.NOT_FOUND);
    }

}
