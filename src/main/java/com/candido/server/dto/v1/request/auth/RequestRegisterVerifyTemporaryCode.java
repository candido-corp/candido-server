package com.candido.server.dto.v1.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RequestRegisterVerifyTemporaryCode(
        @JsonProperty("temporary_code") String temporaryCode
) {}
