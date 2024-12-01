package com.candido.server.dto.v1.response.geo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GeoCountryDto {

    @JsonProperty("country_id")
    private int countryId;

    @JsonProperty("country_name")
    private String countryName;
}
