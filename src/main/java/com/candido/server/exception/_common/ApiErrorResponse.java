package com.candido.server.exception._common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * ApiError represents a response object for custom exceptions in the application.
 * It contains information such as the error code, data, category, and sub-category.
 */
@Getter
public class ApiErrorResponse {

    /**
     * The HTTP status associated with the exception.
     */
    @JsonProperty("status")
    private final int status;

    /**
     * The timestamp indicating when this response instance was created.
     */
    @JsonProperty("timestamp")
    private final LocalDateTime timestamp;

    /**
     * Additional details about the exception.
     */
    @JsonProperty("errors")
    private final List<ApiError> errors;

    /**
     * Constructs a new ApiErrorResponse object with the provided exception message, HTTP status, and timestamp.
     *
     * @param httpStatus The HTTP status associated with the exception.
     * @param errors     The list of error messages.
     */
    public ApiErrorResponse(HttpStatus httpStatus, List<ApiError> errors) {
        this.status = httpStatus.value();
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }

}
