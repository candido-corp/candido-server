package com.candido.server.exception._common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * ApiError represents a response object for custom exceptions in the application.
 * It contains information such as the error code, data, category, and sub-category.
 *
 * @param code        The error code.
 * @param data        The data associated with the error.
 * @param category    The category of the error.
 * @param subCategory The sub-category of the error.
 */
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(
        @JsonProperty("code") String code,
        @JsonProperty("data") Map<String, Object> data,
        @JsonProperty("category") String category,
        @JsonProperty("sub_category") String subCategory,
        @JsonProperty("message") String message
) {

    /**
     * Constructs a new ApiError object with the provided error code, data, category, sub-category, and message.
     *
     * @param code        The error code.
     * @param data        The data associated with the error.
     * @param category    The category of the error.
     * @param subCategory The sub-category of the error.
     * @param message     The error message.
     */
    public ApiError(String code, Map<String, Object> data, String category, String subCategory, String message) {
        this.code = code;
        this.data = data;
        this.category = category;
        this.subCategory = subCategory;
        this.message = message;
    }

}
