package com.candido.server.dto.v1.response.geo;

import com.candido.server.domain.v1.geo.Territory;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GeoProvinceDto {

    @JsonProperty("province_id")
    private int provinceId;

    @JsonProperty("province_name")
    private String provinceName;

    @JsonProperty("mother_relationship")
    private Territory motherRelationship;

}
