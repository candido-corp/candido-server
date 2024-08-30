package com.candido.server.dto.v1.util;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.user.User;

public class AccountUserPair {
    private final Account account;
    private final User user;

    public AccountUserPair(Account account, User user) {
        this.account = account;
        this.user = user;
    }

    public Account getAccount() {
        return account;
    }

    public User getUser() {
        return user;
    }
}

