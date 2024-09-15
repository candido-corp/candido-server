package com.candido.server.service.base.user;

import com.candido.server.domain.v1.user.Gender;
import com.candido.server.domain.v1.user.GenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenderServiceImpl implements GenderService {

    private final GenderRepository genderRepository;

    @Autowired
    public GenderServiceImpl(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    @Override
    public List<Gender> findAll() {
        return genderRepository.findAll();
    }
}
