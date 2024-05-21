package com.candido.server.exception._common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * ApiError represents a response object for custom exceptions in the application.
 * It contains information such as the error code, data, category, and sub-category.
 */
@Getter
@Builder
public class ApiError {

    /**
     * The error code.
     */
    @JsonProperty("code")
    private final String code;

    /**
     * The data associated with the error.
     */
    @JsonProperty("data")
    private final Map<String, Object> data;

    /**
     * The category of the error.
     */
    @JsonProperty("category")
    private final String category;

    /**
     * The sub-category of the error.
     */
    @JsonProperty("sub_category")
    private final String subCategory;

    /**
     * Constructs a new ApiError object with the provided code, data, category, and sub-category.
     *
     * @param code The error code.
     * @param data The data associated with the error.
     * @param category The category of the error.
     * @param subCategory The sub-category of the error.
     */
    public ApiError(String code, Map<String, Object> data, String category, String subCategory) {
        this.code = code;
        this.data = data;
        this.category = category;
        this.subCategory = subCategory;
    }

}
