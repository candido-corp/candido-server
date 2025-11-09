package com.candido.server.service.base.user;

import com.candido.server.domain.v1.user.User;
import com.candido.server.dto.v1.request.account.RequestUpdateUser;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface UserService {
    User getAuthenticatedUser(Authentication authentication);
    Optional<User> findUserByAccountId(Long accountId);
    User findUserByAccountIdOrThrow(Long accountId);
    User save(User user);
    User save(User user, RequestUpdateUser requestUpdateUser, boolean canChangeName);
}
