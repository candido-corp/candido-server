package com.candido.server.dto.v1.response.geo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ResponseGeoTerritoryList {

    @JsonProperty("label_id")
    private int labelId;

    @JsonProperty("label_name")
    private String labelName;

    @JsonProperty("territory_list")
    private List<ResponseGeoTerritory> territoryList;

}
