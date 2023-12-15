package com.candido.server.domain.v1.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenScopeCategoryEnum {
    BTD_RW(1),
    BTD_REGISTRATION(2),
    BTD_RESET(3);

    private final int tokenScopeCategoryId;

}
