package com.candido.server.dto.v1.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record RequestAuthentication (
        @JsonProperty("email") @NotBlank String email,
        @JsonProperty("password") @NotBlank String password
) {}
