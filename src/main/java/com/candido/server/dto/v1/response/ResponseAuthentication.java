package com.candido.server.dto.v1.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseAuthentication (
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("expires_in") String expiresIn
) {}
