package com.candido.server.domain.v1.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenTypeEnum {
    BEARER(1);

    private final int tokenTypeId;
}
