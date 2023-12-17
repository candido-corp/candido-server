package com.candido.server.dto.v1.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RequestAuthentication (
        @JsonProperty("email") String email,
        @JsonProperty("password") String password
) {}
