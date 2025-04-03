package com.candido.server.service.base.auth.token;

import com.candido.server.config.ConfigTemporaryCode;
import com.candido.server.domain.v1.token.TemporaryCode;
import com.candido.server.domain.v1.token.TemporaryCodeRepository;
import com.candido.server.domain.v1.token.TemporaryCode_;
import com.candido.server.util.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.DecimalFormat;

@Service
public class TemporaryCodePoolServiceImpl implements TemporaryCodePoolService {

    private final TemporaryCodeRepository temporaryCodeRepository;

    private final UtilService utilService;

    @Autowired
    public TemporaryCodePoolServiceImpl(
            TemporaryCodeRepository temporaryCodeRepository,
            UtilService utilService
    ) {
        this.temporaryCodeRepository = temporaryCodeRepository;
        this.utilService = utilService;
    }

    @Override
    public long countCodeList(Specification<TemporaryCode> specification) {
        return specification == null ? temporaryCodeRepository.count() :
                temporaryCodeRepository.count(specification);
    }

    @Override
    public boolean isTemporaryCodePoolEmpty() {
        return countCodeList((root, query, criteriaBuilder) -> root.get(TemporaryCode_.TOKEN_ID).isNull()) == 0;
    }

    @Async
    @Override
    public void checkTemporaryCodePoolSize() {
        long currentPoolSize = countCodeList((root, query, criteriaBuilder) -> root.get(TemporaryCode_.TOKEN_ID).isNull());
        if (currentPoolSize < ConfigTemporaryCode.MIN_TEMPORARY_CODE_POOL_SIZE) {
            for (int i = 0; i < ConfigTemporaryCode.MIN_TEMPORARY_CODE_POOL_SIZE - currentPoolSize; i++) {
                generateCode(null);
            }
        }
    }

    public String generateFormattedNumber(int randomNumber, int digits) {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMinimumIntegerDigits(digits);
        decimalFormat.setGroupingUsed(false);
        return decimalFormat.format(randomNumber);
    }

    @Override
    public TemporaryCode generateCode(Long tokenId) {
        SecureRandom random = new SecureRandom();
        int randomNumber;
        String formattedNumber;
        int digits = utilService.countDigits(ConfigTemporaryCode.MAX_TEMPORARY_CODE_SIZE - 1L);

        do {
            randomNumber = random.nextInt(ConfigTemporaryCode.MAX_TEMPORARY_CODE_SIZE);
            formattedNumber = generateFormattedNumber(randomNumber, digits);
        } while(temporaryCodeRepository.findByCode(formattedNumber).isPresent());

        var temporaryCode = TemporaryCode.builder()
                .code(formattedNumber)
                .tokenId(tokenId)
                .expirationDate(null)
                .build();

        return temporaryCodeRepository.save(temporaryCode);
    }

}
