package com.candido.server.service.mapper;

import com.candido.server.domain.v1.user.User;
import com.candido.server.dto.v1.util.UserDto;

public interface UserMapperService {
    UserDto userToUserDto(User user);
}
