package com.candido.server.exception._common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * BTExceptionResponse represents a response object for custom exceptions in the application.
 * It contains information such as the exception message, HTTP status, and the timestamp when the object instance was created.
 */
@Getter
public class BTExceptionResponse {

    /**
     * The message associated with the exception.
     */
    @JsonProperty("message")
    private final String message;

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
     * Constructs a new BTExceptionResponse object with the provided message, HTTP status, and the current timestamp.
     *
     * @param message    The exception message.
     * @param httpStatus The HTTP status associated with the exception.
     */
    public BTExceptionResponse(String message, HttpStatus httpStatus) {
        this.message = message;
        this.status = httpStatus.value();
        this.timestamp = LocalDateTime.now();
    }

}
