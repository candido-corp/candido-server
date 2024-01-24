package com.candido.server.dto.v1.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDto (
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName,
        @JsonProperty("gender") GenderDto gender,
        @JsonProperty("address") AddressDto address,
        @JsonProperty("birthdate") String birthdate,
        @JsonProperty("mobile_number") String mobileNumber,
        @JsonProperty("phone_number") String phoneNumber,
        @JsonProperty("created_at") String createdAt,
        @JsonProperty("deleted_at") String deletedAt
) {}
