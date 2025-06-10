package com.candido.server.dto.v1.response.geo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseAddressType {

    @JsonProperty("address_type_id")
    private int addressTypeId;

    @JsonProperty("address_type_key")
    private String addressTypeKey;

    @JsonProperty("description")
    private String description;

}
