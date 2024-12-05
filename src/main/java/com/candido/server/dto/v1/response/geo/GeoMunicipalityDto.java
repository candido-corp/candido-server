package com.candido.server.dto.v1.response.geo;

import com.candido.server.domain.v1.geo.Territory;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GeoMunicipalityDto {

    @JsonProperty("municipality_id")
    private int municipalityId;

    @JsonProperty("municipality_name")
    private String municipalityName;

    @JsonProperty("mother_relationship")
    private Territory motherRelationship;

}
