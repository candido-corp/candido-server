package com.candido.server.service.base.mapper;

import com.candido.server.domain.v1.user.Gender;
import com.candido.server.dto.v1.util.GenderDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenderMapperServiceImpl implements GenderMapperService {
    @Override
    public List<GenderDto> genderToGenderDto(List<Gender> genderList) {
        if (genderList == null) return null;

        List<GenderDto> list = new ArrayList<GenderDto>(genderList.size());
        for (Gender gender : genderList) {
            list.add(genderToGenderDto1(gender));
        }

        return list;
    }

    protected GenderDto genderToGenderDto1(Gender gender) {
        if (gender == null) return null;

        int id = gender.getId();
        String description = gender.getDescription();

        return new GenderDto(id, description);
    }
}
