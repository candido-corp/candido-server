package com.candido.server.service.base.user;

import com.candido.server.domain.v1.user.User;
import com.candido.server.dto.v1.request.account.RequestUpdateUserDto;
import com.candido.server.dto.v1.request.geo.RequestAddressDto;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByAccountId(int accountId);
    User findUserByAccountIdOrThrow(int accountId);
    User save(User user);
    User save(User user, RequestUpdateUserDto requestUpdateUserDto, boolean canChangeName);
    User updateAddress(User user, RequestAddressDto requestUserAddressDto);
    void deleteAddress(User user);
}
