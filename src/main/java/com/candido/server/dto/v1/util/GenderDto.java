package com.candido.server.dto.v1.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GenderDto (
        @JsonProperty("id") int id,
        @JsonProperty("description") String description
) {}
