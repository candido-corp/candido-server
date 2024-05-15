package com.candido.server.service.base.mapstruct;

import com.candido.server.domain.v1.user.Gender;
import com.candido.server.dto.v1.util.GenderDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenderMapper {

    GenderMapper INSTANCE = Mappers.getMapper( GenderMapper.class );

    List<GenderDto> genderToGenderDto(List<Gender> genderList);
}