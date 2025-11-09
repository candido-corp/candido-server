package com.candido.server.error;

import com.candido.server.exception._common.CustomExceptionResolver;
import com.candido.server.exception._common.CustomRuntimeException;
import com.candido.server.exception._common.ApiErrorResponse;
import com.candido.server.exception._common.resolver.EnumMessageResolverType;
import com.candido.server.exception.account.ExceptionAccountDisabled;
import com.candido.server.exception.security.auth.*;
import com.candido.server.exception.security.jwt.ExceptionInvalidJWTToken;
import com.candido.server.exception.security.jwt.ExceptionSecurityJwt;
import com.candido.server.exception.util.ExceptionValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
public class SecurityErrorAdvice {

    private final CustomExceptionResolver customExceptionResolver;

    @ExceptionHandler({
            ExceptionForbidden.class
    })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ApiErrorResponse> handleForbiddenException(CustomRuntimeException ex, Locale locale) {
        return customExceptionResolver.resolveException(
                ex,
                locale,
                HttpStatus.FORBIDDEN,
                EnumMessageResolverType.AUTHENTICATION
        );
    }

    @ExceptionHandler({
            ExceptionVerifyRegistrationToken.class,
            ExceptionInvalidJWTToken.class,
            ExceptionVerifyResetToken.class,
            ExceptionToken.class,
            ExceptionSecurityJwt.class,
            ExceptionAccountDisabled.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Void> handleTokenException(CustomRuntimeException ex) {
        customExceptionResolver.printException(ex);
        return null;
    }

    @ExceptionHandler({ ExceptionValidationAuth.class, ExceptionValidation.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(ExceptionValidationAuth ex, Locale locale) {
        return customExceptionResolver.resolveException(
                ex,
                locale,
                HttpStatus.BAD_REQUEST,
                EnumMessageResolverType.VALIDATION
        );
    }

    @ExceptionHandler({ ExceptionAuth.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(ExceptionAuth ex, Locale locale) {
        return customExceptionResolver.resolveException(
                ex,
                locale,
                HttpStatus.BAD_REQUEST,
                EnumMessageResolverType.AUTHENTICATION
        );
    }

    @ExceptionHandler({ ExceptionInvalidToken.class })
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ApiErrorResponse> handleInvalidTokenException(ExceptionInvalidToken ex, Locale locale) {
        return customExceptionResolver.resolveException(
                ex,
                locale,
                HttpStatus.UNPROCESSABLE_ENTITY,
                EnumMessageResolverType.VALIDATION
        );
    }


}
