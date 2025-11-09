package com.candido.server.dto.v1.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ResponseAuthentication (
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("expires_in") int expiresIn,
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("refresh_expires_in") int refreshExpiresIn
) {}
