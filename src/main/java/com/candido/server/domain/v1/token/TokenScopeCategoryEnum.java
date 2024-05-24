package com.candido.server.domain.v1.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenScopeCategoryEnum {
    RW(1),
    REGISTRATION(2),
    RESET(3);

    private final int tokenScopeCategoryId;

}
