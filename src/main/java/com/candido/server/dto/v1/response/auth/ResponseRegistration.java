package com.candido.server.dto.v1.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseRegistration {

    @JsonProperty("session_id")
    private String sessionId;

}
