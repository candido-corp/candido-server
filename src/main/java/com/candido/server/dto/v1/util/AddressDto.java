package com.candido.server.dto.v1.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddressDto (
        @JsonProperty("id") int id,
        @JsonProperty("country") String country,
        @JsonProperty("region") String region,
        @JsonProperty("city") String city,
        @JsonProperty("postal_code") String postalCode,
        @JsonProperty("street") String street,
        @JsonProperty("house_number") String houseNumber
) {}
