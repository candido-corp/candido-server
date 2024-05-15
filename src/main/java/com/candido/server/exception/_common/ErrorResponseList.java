package com.candido.server.exception._common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ErrorResponseList represents a response object containing a list of error messages,
 * along with HTTP status and timestamp.
 * It is typically used to encapsulate multiple error messages in the response.
 */
@Getter
public class ErrorResponseList {

    /**
     * The list of error messages.
     */
    @JsonProperty("messages")
    private final List<String> messages;

    /**
     * The HTTP status code.
     */
    @JsonProperty("status")
    private final int status;

    /**
     * The timestamp when the error response was created.
     */
    @JsonProperty("timestamp")
    private final LocalDateTime timestamp;

    /**
     * Constructs a new ErrorResponseList object with the provided list of error messages,
     * HTTP status, and current timestamp.
     *
     * @param messages   The list of error messages.
     * @param httpStatus The HTTP status associated with the error response.
     */
    public ErrorResponseList(List<String> messages, HttpStatus httpStatus) {
        this.messages = messages;
        this.status = httpStatus.value();
        this.timestamp = LocalDateTime.now();
    }

}
