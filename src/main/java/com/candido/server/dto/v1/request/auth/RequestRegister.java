package com.candido.server.dto.v1.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record RequestRegister (
        @JsonProperty("email") @NotBlank String email,
        @JsonProperty("password") @NotBlank String password,
        @JsonProperty("confirm_password") @NotBlank String confirmPassword
) {}
