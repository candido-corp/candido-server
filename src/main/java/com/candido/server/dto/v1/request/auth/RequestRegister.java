package com.candido.server.dto.v1.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestRegister (
        @JsonProperty("email") @NotNull @NotBlank String email,
        @JsonProperty("password") @NotNull @NotBlank String password,
        @JsonProperty("confirm_password") @NotNull @NotBlank String confirmPassword,
        @JsonProperty("first_name") @NotNull @NotBlank String firstName,
        @JsonProperty("last_name") @NotNull @NotBlank String lastName
) {}
