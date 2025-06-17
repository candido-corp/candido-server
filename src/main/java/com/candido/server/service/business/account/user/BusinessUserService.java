package com.candido.server.service.business.account.user;

import com.candido.server.dto.v1.request.account.RequestUpdateUser;
import com.candido.server.dto.v1.util.UserDto;
import org.springframework.security.core.Authentication;

public interface BusinessUserService {
    UserDto getUserDtoWithPrimaryAddress(Authentication authentication);
    UserDto editUserInfo(Authentication authentication, RequestUpdateUser requestUpdateUser);
}
