package com.candido.server.error.user;

import com.candido.server.exception._common.BTExceptionResolver;
import com.candido.server.exception._common.BTExceptionResponse;
import com.candido.server.exception._common.BTExceptionResponseList;
import com.candido.server.exception.account.*;
import com.candido.server.exception.user.UserNotFoundException;
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

@Hidden
@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class UserErrorAdvice {

    private final BTExceptionResolver btExceptionResolver;

    @ExceptionHandler({UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<BTExceptionResponse> handleUserNotFoundException(UserNotFoundException ex, Locale locale) {
        log.info("[EXCEPTION] ({}) -> {}", UserNotFoundException.class.getName(), LocalDateTime.now());
        return btExceptionResolver.resolveBusinessBTException(ex, locale, HttpStatus.NOT_FOUND);
    }

}
