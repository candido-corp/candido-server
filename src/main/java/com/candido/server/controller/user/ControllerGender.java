package com.candido.server.controller.user;

import com.candido.server.domain.v1.user.Gender;
import com.candido.server.dto.v1.util.GenderDto;
import com.candido.server.service.mapstruct.GenderMapper;
import com.candido.server.service.user.GenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genders")
public class ControllerGender {

    @Autowired
    GenderService genderService;

    @Autowired
    GenderMapper genderMapper;

    @GetMapping
    public ResponseEntity<List<GenderDto>> getGenders() {
        List<Gender> genderList = genderService.findAll();
        List<GenderDto> genderDtoList = genderMapper.genderToGenderDto(genderList);
        return ResponseEntity.ok(genderDtoList);
    }

}
