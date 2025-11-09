package com.candido.server.exception._common;

import com.candido.server.exception._common.resolver.EnumMessageResolverType;
import com.candido.server.exception.account.ExceptionInvalidPasswordAccountList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Locale;

public interface ExceptionResolver {

    void printException(Exception ex, String ...extraMessage);
    void printException(CustomRuntimeException ex);

    ResponseEntity<ApiErrorResponse> resolveException(
            CustomRuntimeException ex,
            Locale locale,
            HttpStatus httpStatus,
            EnumMessageResolverType type
    );

    ResponseEntity<ApiErrorResponse> resolveException(
            ExceptionInvalidPasswordAccountList ex,
            Locale locale,
            HttpStatus httpStatus,
            EnumMessageResolverType type
    );

}
