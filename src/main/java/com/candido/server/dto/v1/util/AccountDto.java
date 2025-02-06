package com.candido.server.dto.v1.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccountDto(
        @JsonProperty("email") String email,
        @JsonProperty("status") String status,
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName
) {}
