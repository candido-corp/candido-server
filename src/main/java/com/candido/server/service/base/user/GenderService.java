package com.candido.server.service.base.user;

import com.candido.server.domain.v1.user.Gender;

import java.util.List;
import java.util.Optional;

public interface GenderService {
    List<Gender> findAll();
    Optional<Gender> findGenderById(int id);
    Gender findGenderByIdOrThrow(int id);
}
