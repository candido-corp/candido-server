package com.candido.server.error;

import com.candido.server.exception._common.CustomExceptionResolver;
import com.candido.server.exception._common.CustomRuntimeException;
import com.candido.server.exception._common.ApiErrorResponse;
import com.candido.server.exception._common.resolver.EnumMessageResolverExceptionType;
import com.candido.server.exception.account.ExceptionAccountDisabled;
import com.candido.server.exception.security.auth.*;
import com.candido.server.exception.security.jwt.ExceptionInvalidJWTToken;
import com.candido.server.exception.security.jwt.ExceptionSecurityJwt;
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
            ExceptionVerifyRegistrationToken.class,
            ExceptionInvalidJWTToken.class,
            ExceptionVerifyResetToken.class,
            ExceptionToken.class,
            ExceptionTemporaryCode.class,
            ExceptionSecurityJwt.class,
            ExceptionAccountDisabled.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Void> handleTokenException(CustomRuntimeException ex) {
        customExceptionResolver.printException(ex);
        return null;
    }

    @ExceptionHandler({ ExceptionAuth.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(ExceptionAuth ex, Locale locale) {
        return customExceptionResolver.resolveException(
                ex,
                locale,
                HttpStatus.BAD_REQUEST,
                EnumMessageResolverExceptionType.AUTHENTICATION
        );
    }


}
