package com.candido.server.dto.v1.response.geo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GeoTerritoryDto {

    @JsonProperty("territory_id")
    private int territoryId;

    @JsonProperty("territory_name")
    private String territoryName;

}
