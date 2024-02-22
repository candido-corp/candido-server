package com.candido.server.dto.v1.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestRegisterVerifyTemporaryCode(
        @JsonProperty("temporary_code") @NotNull @NotBlank String temporaryCode
) {}
