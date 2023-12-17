package com.candido.server.dto.v1.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccountDto(
        @JsonProperty("email") String email,
        @JsonProperty("status") String status
) {}
