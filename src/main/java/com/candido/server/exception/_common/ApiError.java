package com.candido.server.exception._common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;
import java.util.Map;

/**
 * ApiError represents a response object for custom exceptions in the application.
 * It contains information such as the error code, data, category, and sub-category.
 *
 * @param code The error code.
 * @param data The data associated with the error.
 */
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(
        @JsonProperty("fields") List<String> fields,
        @JsonProperty("code") String code,
        @JsonProperty("data") Map<String, Object> data,
        @JsonProperty("message") String message
) {}
