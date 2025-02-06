package com.candido.server.dto.v1.response.geo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseUserAddressTerritory {

    @JsonProperty("order_id")
    private int orderId;

    @JsonProperty("label_id")
    private int labelId;

    @JsonProperty("label_name")
    private String labelName;

    @JsonUnwrapped
    private ResponseGeoTerritoryDto territory;

}
