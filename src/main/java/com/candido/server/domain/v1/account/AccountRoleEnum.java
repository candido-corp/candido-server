package com.candido.server.domain.v1.account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
public enum AccountRoleEnum {
    ADMIN(1,
            Set.of(
                    AccountPermissionEnum.ADMIN_READ,
                    AccountPermissionEnum.ADMIN_CREATE,
                    AccountPermissionEnum.ADMIN_UPDATE,
                    AccountPermissionEnum.ADMIN_DELETE
            )
    ),

    USER(2, Collections.emptySet());

    @Getter
    private final int roleId;

    @Getter
    private final Set<AccountPermissionEnum> accountPermissionEnums;

}
