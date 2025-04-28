package com.candido.server.dto.v1.request.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record RequestUpdateUser(
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName,
        @JsonProperty("gender_id") Integer genderId,
        @JsonProperty("birthdate") LocalDate birthdate,
        @JsonProperty("mobile_number") String mobileNumber,
        @JsonProperty("phone_number") String phoneNumber
) {}
