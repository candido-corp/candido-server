package com.candido.server.dto.v1.request.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RequestUpdateUserDto (
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName,
        @JsonProperty("gender_id") int genderId,
        @JsonProperty("birthdate") LocalDate birthdate,
        @JsonProperty("mobile_number") String mobileNumber,
        @JsonProperty("phone_number") String phoneNumber
) {}
