package com.candido.server.domain.v1.account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountStatusEnum {
    PENDING(1),
    VERIFIED(2),
    DISABLED(3);

    private final int statusId;
}
