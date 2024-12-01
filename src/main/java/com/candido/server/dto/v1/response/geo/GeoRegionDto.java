package com.candido.server.dto.v1.response.geo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GeoRegionDto {

    @JsonProperty("country_id")
    @Column(name = "country_id")
    private int countryId;

    @JsonProperty("region_id")
    @Column(name = "region_id")
    private int regionId;

    @JsonProperty("region_name")
    @Column(name = "region_name")
    private String regionName;

}
