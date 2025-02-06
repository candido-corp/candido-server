package com.candido.server.controller.gender;

import com.candido.server.domain.v1.user.Gender;
import com.candido.server.dto.v1.util.GenderDto;
import com.candido.server.service.base.mapper.GenderMapperService;
import com.candido.server.service.base.user.GenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genders")
public class ControllerGender {

    private final GenderService genderService;
    private final GenderMapperService genderMapper;

    @Autowired
    public ControllerGender(
            GenderService genderService,
            GenderMapperService genderMapper
    ) {
        this.genderService = genderService;
        this.genderMapper = genderMapper;
    }

    @GetMapping
    public ResponseEntity<List<GenderDto>> getGenders() {
        List<Gender> genderList = genderService.findAll();
        List<GenderDto> genderDtoList = genderMapper.genderToGenderDto(genderList);
        return ResponseEntity.ok(genderDtoList);
    }

}
