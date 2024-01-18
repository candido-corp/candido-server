package com.candido.server.service.mapstruct;

import com.candido.server.domain.v1.user.User;
import com.candido.server.dto.v1.util.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    UserDto userToUserDto(User user);
}
