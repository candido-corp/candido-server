package com.candido.server.service.base.user;

import com.candido.server.domain.v1.user.Gender;
import com.candido.server.domain.v1.user.GenderRepository;
import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.user.ExceptionAddressNotFound;
import com.candido.server.exception.user.ExceptionUserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Gender> findGenderById(int id) {
        return genderRepository.findById(id);
    }

    @Override
    public Gender findGenderByIdOrThrow(int id) {
        return findGenderById(id)
                .orElseThrow(() -> new ExceptionAddressNotFound(EnumExceptionName.ERROR_BUSINESS_ADDRESS_NOT_FOUND.name()));
    }

}
