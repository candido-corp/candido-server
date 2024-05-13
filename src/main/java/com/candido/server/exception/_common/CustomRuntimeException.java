package com.candido.server.exception._common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * CustomRuntimeException is a base class for custom runtime exceptions in the application.
 * It provides fields for exception message, arguments, HTTP status, and timestamp.
 */
@Getter
public class CustomRuntimeException extends RuntimeException {

    /**
     * The message associated with the exception.
     */
    private String message;

    /**
     * Arguments associated with the exception message.
     */
    private Object[] args;

    /**
     * The HTTP status associated with the exception.
     */
    private HttpStatus status;

    /**
     * The timestamp indicating when this exception occurred.
     */
    private LocalDateTime timestamp;

    /**
     * Constructs a new CustomRuntimeException with no detail message.
     */
    public CustomRuntimeException() {
        super();
    }

    /**
     * Constructs a new CustomRuntimeException with the specified detail message.
     *
     * @param message The detail message.
     */
    public CustomRuntimeException(String message) {
        super();
        this.message = message;
    }

    /**
     * Constructs a new CustomRuntimeException with the specified detail message and arguments.
     *
     * @param message The detail message.
     * @param args    Arguments associated with the detail message.
     */
    public CustomRuntimeException(String message, Object[] args) {
        super();
        this.message = message;
        this.args = args;
    }

    /**
     * Constructs a new CustomRuntimeException with the specified detail message, HTTP status, and timestamp.
     *
     * @param message    The detail message.
     * @param status     The HTTP status.
     * @param timestamp  The timestamp.
     */
    public CustomRuntimeException(String message, HttpStatus status, LocalDateTime timestamp) {
        super();
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }

}
