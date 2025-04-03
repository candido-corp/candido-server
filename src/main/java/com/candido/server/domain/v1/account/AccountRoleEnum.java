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

    USER_VERIFIED(2,
            Set.of(
                    AccountPermissionEnum.USER_READ,
                    AccountPermissionEnum.USER_CREATE,
                    AccountPermissionEnum.USER_UPDATE,
                    AccountPermissionEnum.USER_DELETE
            )
    ),

    USER_NOT_VERIFIED(3, Collections.emptySet());

    @Getter
    private final int roleId;

    @Getter
    private final Set<AccountPermissionEnum> accountPermissionEnums;

}
