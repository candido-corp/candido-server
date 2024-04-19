package com.candido.server.dto.v1.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RequestPasswordResetEmail (
        @JsonProperty("email") String email
) {}
