package com.candido.server.dto.v1.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record RequestPasswordReset (
        @JsonProperty("password") @NotBlank String password,
        @JsonProperty("confirm_password") @NotBlank String confirmPassword
) {}
