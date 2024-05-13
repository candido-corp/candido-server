package com.candido.server.error.security;

import com.candido.server.exception._common.CustomRuntimeException;
import com.candido.server.exception._common.BTExceptionResolver;
import com.candido.server.exception._common.BTExceptionResponse;
import com.candido.server.exception.security.auth.*;
import com.candido.server.exception.security.jwt.InvalidJWTTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class SecurityErrorAdvice {

    @Autowired
    BTExceptionResolver btExceptionResolver;

    @ExceptionHandler({
            VerifyRegistrationTokenException.class,
            InvalidJWTTokenException.class,
            VerifyResetTokenException.class,
            TokenException.class,
            TemporaryCodeException.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Void> handleTokenException(CustomRuntimeException ex, Locale locale) {
        log.info("[EXCEPTION] ({}) -> {}", CustomRuntimeException.class.getName(), LocalDateTime.now());
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({AuthException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BTExceptionResponse> handleAuthenticationException(AuthException ex, Locale locale) {
        log.info("[EXCEPTION] ({}) -> {}", AuthenticationException.class.getName(), LocalDateTime.now());
        return btExceptionResolver.resolveAuthenticationBTException(ex, locale, HttpStatus.BAD_REQUEST);
    }


}
