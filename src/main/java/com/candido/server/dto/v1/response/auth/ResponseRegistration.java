package com.candido.server.dto.v1.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseRegistration {

    @JsonProperty("t")
    private String t;

    @JsonProperty("e")
    private String e;

}
