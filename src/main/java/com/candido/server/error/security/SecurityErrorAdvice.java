package com.candido.server.error.security;

import com.candido.server.exception._common.CustomRuntimeException;
import com.candido.server.exception._common.BTExceptionResolver;
import com.candido.server.exception._common.ErrorResponse;
import com.candido.server.exception.security.auth.*;
import com.candido.server.exception.security.jwt.ExceptionInvalidJWTToken;
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
            ExceptionVerifyRegistrationToken.class,
            ExceptionInvalidJWTToken.class,
            ExceptionVerifyResetToken.class,
            ExceptionToken.class,
            ExceptionTemporaryCode.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Void> handleTokenException(CustomRuntimeException ex, Locale locale) {
        log.info("[EXCEPTION] ({}) -> {}", CustomRuntimeException.class.getName(), LocalDateTime.now());
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ExceptionAuth.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(ExceptionAuth ex, Locale locale) {
        log.info("[EXCEPTION] ({}) -> {}", AuthenticationException.class.getName(), LocalDateTime.now());
        return btExceptionResolver.resolveAuthenticationBTException(ex, locale, HttpStatus.BAD_REQUEST);
    }


}
