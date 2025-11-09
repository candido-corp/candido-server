package com.candido.server.dto.v1.util;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.user.User;
import lombok.Getter;

public record AccountUserPairDto(
        Account account,
        User user
) {}

