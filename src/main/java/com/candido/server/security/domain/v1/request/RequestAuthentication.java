package com.candido.server.security.domain.v1.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RequestAuthentication (
        @JsonProperty("email") String email,
        @JsonProperty("password") String password
) {}
