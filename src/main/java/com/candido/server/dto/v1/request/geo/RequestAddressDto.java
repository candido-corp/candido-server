package com.candido.server.dto.v1.request.geo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RequestAddressDto(
        @JsonProperty("territory_id") Integer territoryId,
        @JsonProperty("type_id") Integer addressTypeId,
        @JsonProperty("zip") String zip,
        @JsonProperty("street") String street,
        @JsonProperty("house_number") String houseNumber
) {}