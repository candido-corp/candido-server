package com.candido.server.service.user;

import com.candido.server.domain.v1.user.User;
import com.candido.server.dto.v1.request.account.RequestUpdateUserDto;

import java.util.Optional;

public interface UserService {
    Optional<User> findByAccountId(int accountId);
    User save(User user);
    User save(User user, RequestUpdateUserDto requestUpdateUserDto);
}
