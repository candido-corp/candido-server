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
public class GenderController {

    @Autowired
    GenderService genderService;

    @Autowired
    GenderMapper genderMapper;

    @GetMapping
    public ResponseEntity<List<GenderDto>> getAllGenders() {
        List<Gender> genderList = genderService.findAll();
        return ResponseEntity.ok(genderMapper.genderToGenderDto(genderList));
    }

}
