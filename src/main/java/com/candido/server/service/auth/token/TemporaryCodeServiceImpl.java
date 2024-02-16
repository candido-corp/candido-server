package com.candido.server.service.auth.token;

import com.candido.server.domain.v1.token.TemporaryCode;
import com.candido.server.domain.v1.token.TemporaryCodeRepository;
import com.candido.server.domain.v1.token.TemporaryCode_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@Service
public class TemporaryCodeServiceImpl implements TemporaryCodeService {

    @Autowired
    private TemporaryCodeRepository temporaryCodeRepository;

    @Override
    public Optional<TemporaryCode> findByCode(String code) {
        Specification<TemporaryCode> byCode = ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(TemporaryCode_.CODE), code));
        return temporaryCodeRepository.findOne(byCode);
    }

    @Override
    public TemporaryCode generateCode() {
        SecureRandom random = new SecureRandom();
        int randomNumber = 0;
        String formattedNumber = "";

        do {
            randomNumber = random.nextInt(1000000);
            formattedNumber = String.format("%06d", randomNumber);
        } while(findByCode(formattedNumber).isPresent());

        return temporaryCodeRepository.save(TemporaryCode.builder().code(formattedNumber).build());
    }

    @Override
    public void delete(String code) {
        findByCode(code).ifPresent(temporaryCode -> temporaryCodeRepository.delete(temporaryCode));
    }

}
