package com.candido.server.dto.v1.util;

import com.candido.server.domain.v1.user.Address;
import com.candido.server.domain.v1.user.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDto (
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName,
        @JsonProperty("gender") Gender gender,
        @JsonProperty("address") Address address,
        @JsonProperty("birthdate") String birthdate,
        @JsonProperty("mobile_number") String mobileNumber,
        @JsonProperty("phone_number") String phoneNumber,
        @JsonProperty("created_at") String createdAt,
        @JsonProperty("deleted_at") String deletedAt
) {}
