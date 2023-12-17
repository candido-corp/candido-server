package com.candido.server.dto.v1.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RequestRegister (
        @JsonProperty("email") String email,
        @JsonProperty("password") String password,
        @JsonProperty("confirm_password") String confirmPassword
) {}
