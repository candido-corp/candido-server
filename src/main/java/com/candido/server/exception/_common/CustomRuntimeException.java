package com.candido.server.exception._common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * CustomRuntimeException is a base class for custom runtime exceptions in the application.
 * It provides fields for exception message, arguments, HTTP status, and timestamp.
 */
@Getter
public class CustomRuntimeException extends RuntimeException {

    /**
     * The message associated with the exception.
     */
    private final String message;

    /**
     * Extra messages associated with the exception.
     */
    private final String[] extraMessages;

    /**
     * Arguments associated with the exception message.
     */
    private final transient Object[] args;

    /**
     * Details associated with the exception.
     */
    private final transient Map<String, Object> details;

    /**
     * The HTTP status associated with the exception.
     */
    private final HttpStatus status;

    /**
     * The timestamp indicating when this exception occurred.
     */
    private final LocalDateTime timestamp;

    /**
     * The list of fields associated with the exception.
     */
    private final List<String> fields;

    /**
     * Constructs a new CustomRuntimeException with no detail message.
     */
    public CustomRuntimeException() {
        super();
        this.message = null;
        this.extraMessages = null;
        this.args = null;
        this.details = null;
        this.status = null;
        this.timestamp = LocalDateTime.now();
        this.fields = null;
    }

    /**
     * Constructs a new CustomRuntimeException with the specified detail message.
     *
     * @param message The detail message.
     */
    public CustomRuntimeException(String message) {
        super(message);
        this.message = message;
        this.extraMessages = null;
        this.args = null;
        this.details = null;
        this.status = null;
        this.timestamp = LocalDateTime.now();
        this.fields = null;
    }

    /**
     * Constructs a new CustomRuntimeException by concatenating multiple detail messages into a single message.
     *
     * @param message The main detail message.
     * @param messages The array of extra detail messages to be concatenated.
     */
    public CustomRuntimeException(String message, String... messages) {
        super(message);
        this.message = message;
        this.extraMessages = messages;
        this.args = null;
        this.details = null;
        this.status = null;
        this.timestamp = LocalDateTime.now();
        this.fields = null;
    }

    /**
     * Constructs a new CustomRuntimeException with the specified detail message and fields.
     *
     * @param message The detail message.
     * @param fields The list of fields associated with the exception.
     */
    public CustomRuntimeException(String message, List<String> fields) {
        super(message);
        this.message = message;
        this.extraMessages = null;
        this.args = null;
        this.details = null;
        this.status = null;
        this.timestamp = LocalDateTime.now();
        this.fields = fields;
    }

    /**
     * Constructs a new CustomRuntimeException with the specified detail message and arguments.
     *
     * @param message The detail message.
     * @param args    Arguments associated with the detail message.
     */
    public CustomRuntimeException(String message, Object[] args) {
        super(message);
        this.message = message;
        this.extraMessages = null;
        this.args = args;
        this.details = null;
        this.status = null;
        this.timestamp = LocalDateTime.now();
        this.fields = null;
    }

    /**
     * Constructs a new CustomRuntimeException with the specified detail message, arguments, and details.
     *
     * @param message The detail message.
     * @param args    Arguments associated with the detail message.
     * @param details Details associated with the exception.
     */
    public CustomRuntimeException(String message, Object[] args, Map<String, Object> details) {
        super(message);
        this.message = message;
        this.extraMessages = null;
        this.args = args;
        this.details = details;
        this.status = null;
        this.timestamp = LocalDateTime.now();
        this.fields = null;
    }

    /**
     * Constructs a new CustomRuntimeException with the specified detail message, arguments, details, and fields.
     *
     * @param message The detail message.
     * @param args    Arguments associated with the detail message.
     * @param details Details associated with the exception.
     * @param fields  The list of fields associated with the exception.
     */
    public CustomRuntimeException(String message, Object[] args, Map<String, Object> details, List<String> fields) {
        super(message);
        this.message = message;
        this.extraMessages = null;
        this.args = args;
        this.details = details;
        this.status = null;
        this.timestamp = LocalDateTime.now();
        this.fields = fields;
    }

    /**
     * Constructs a new CustomRuntimeException with the specified detail message, HTTP status, and timestamp.
     *
     * @param message    The detail message.
     * @param status     The HTTP status.
     * @param timestamp  The timestamp.
     */
    public CustomRuntimeException(String message, HttpStatus status, LocalDateTime timestamp) {
        super(message);
        this.message = message;
        this.extraMessages = null;
        this.args = null;
        this.details = null;
        this.status = status;
        this.timestamp = timestamp;
        this.fields = null;
    }

}
