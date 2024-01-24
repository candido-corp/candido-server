package com.candido.server.service.user;

import com.candido.server.domain.v1.user.Gender;

import java.util.List;

public interface GenderService {
    List<Gender> findAll();
}
