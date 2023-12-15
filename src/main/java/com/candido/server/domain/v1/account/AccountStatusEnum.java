package com.candido.server.domain.v1.account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountStatusEnum {
    Pending(1),
    Enabled(2),
    Disabled(3);

    private final int statusId;
}
