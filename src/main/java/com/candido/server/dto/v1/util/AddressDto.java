package com.candido.server.dto.v1.util;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record AddressDto (
        @JsonProperty("id") int id,
        @JsonProperty("territory_id") int territoryId,
        @JsonProperty("type_id") int typeId,
        @JsonProperty("zip") String zip,
        @JsonProperty("street") String street,
        @JsonProperty("house_number") String houseNumber,
        @JsonProperty("created_at") LocalDateTime createdAt,
        @JsonProperty("updated_at") LocalDateTime updatedAt
) {}
