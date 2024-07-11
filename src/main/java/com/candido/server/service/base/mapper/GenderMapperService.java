package com.candido.server.service.base.mapper;

import com.candido.server.domain.v1.user.Gender;
import com.candido.server.dto.v1.util.GenderDto;

import java.util.List;

public interface GenderMapperService {
    List<GenderDto> genderToGenderDto(List<Gender> genderList);
}
