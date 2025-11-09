package com.candido.server.dto.v1.request.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RequestAccountSettings<T> (
        @JsonProperty("key") String key,
        @JsonProperty("value") T value
) {
}
