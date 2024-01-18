package com.candido.server.service.user;

import com.candido.server.domain.v1.user.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByAccountId(int accountId);
}
