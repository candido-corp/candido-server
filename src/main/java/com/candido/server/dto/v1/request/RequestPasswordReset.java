package com.candido.server.dto.v1.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RequestPasswordReset (
        @JsonProperty("password") String password,
        @JsonProperty("confirm_password") String confirmPassword
) {}
