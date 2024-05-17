package com.candido.server.service.base.auth.token;

import com.candido.server.domain.v1.token.TemporaryCode;
import com.candido.server.domain.v1.token.TemporaryCodeRepository;
import com.candido.server.domain.v1.token.TemporaryCode_;
import com.candido.server.exception.security.auth.ExceptionTemporaryCode;
import com.candido.server.util.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class TemporaryCodeServiceImpl implements TemporaryCodeService {

    /**
     * This represents the max size of the pool for the temporary code.
     */
    private static final int MIN_TEMPORARY_CODE_POOL_SIZE = 20;

    /**
     * This represents the max size of a single temporary code.
     * This is intended to be MAX - 1 because the random algorithm exclude the last value.
     * Basically if the number is 1_000_000 the code would be of 6 digit.
     */
    private static final int MAX_TEMPORARY_CODE_SIZE = 1_000_000;

    /**
     * This represents the max number of seconds (S) that the code can be active.
     */
    private static final int MAX_TEMPORARY_CODE_DURATION = 300;

    @Autowired
    private TemporaryCodeRepository temporaryCodeRepository;

    @Autowired
    private UtilService utilService;

    @Override
    public long countCodeList(Specification<TemporaryCode> specification) {
        return specification == null ? temporaryCodeRepository.count() :
                temporaryCodeRepository.count(specification);
    }

    @Override
    public Optional<TemporaryCode> getFirstCodeNotAssigned() {
        System.out.println(temporaryCodeRepository.findFirstByTokenId(null));
        return countCodeList(null) == 0 ? Optional.empty() : temporaryCodeRepository.findFirstByTokenId(null);
    }

    @Override
    public Optional<TemporaryCode> findByCode(String code) {
        Specification<TemporaryCode> byCode = ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(TemporaryCode_.CODE), code));
        return temporaryCodeRepository.findOne(byCode);
    }

    @Override
    public Optional<TemporaryCode> findByTokenId(long tokenId) {
        Specification<TemporaryCode> byTokenId = ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(TemporaryCode_.TOKEN_ID), tokenId));
        return temporaryCodeRepository.findOne(byTokenId);
    }

    @Override
    public TemporaryCode generateCode(Long tokenId) {
        SecureRandom random = new SecureRandom();
        int randomNumber = 0;
        String formattedNumber = "";
        int digits = utilService.countDigits(MAX_TEMPORARY_CODE_SIZE - 1);

        do {
            randomNumber = random.nextInt(MAX_TEMPORARY_CODE_SIZE);
            formattedNumber = String.format("%0" + digits + "d", randomNumber);
        } while(findByCode(formattedNumber).isPresent());

        var temporaryCode = TemporaryCode.builder()
                .code(formattedNumber)
                .tokenId(tokenId)
                .build();

        return temporaryCodeRepository.save(temporaryCode);
    }

    @Override
    public void deleteTemporaryCodeByCodeString(String code) {
        findByCode(code).ifPresent(temporaryCode -> temporaryCodeRepository.delete(temporaryCode));
    }

    @Override
    public void deleteTemporaryCodeByTokenId(long tokenId) {
        findByTokenId(tokenId).ifPresent(temporaryCode -> temporaryCodeRepository.delete(temporaryCode));
    }

    @Override
    public void deleteTemporaryCodeInstance(TemporaryCode temporaryCode) {
        temporaryCodeRepository.delete(temporaryCode);
    }

    @Override
    public TemporaryCode assignCode(long tokenId) {
        TemporaryCode temporaryCode = getFirstCodeNotAssigned().orElseGet(() -> generateCode(tokenId));
        temporaryCode.setExpirationDate(LocalDateTime.now().plusSeconds(MAX_TEMPORARY_CODE_DURATION));
        if(temporaryCode.getTokenId() == null) {
            temporaryCode.setTokenId(tokenId);
            temporaryCodeRepository.save(temporaryCode);
        }
        checkTemporaryCodePoolSize();
        return temporaryCode;
    }

    @Async
    @Override
    public void checkTemporaryCodePoolSize() {
        long currentPoolSize =  countCodeList(((root, query, criteriaBuilder) -> root.get(TemporaryCode_.TOKEN_ID).isNull()));
        if(currentPoolSize < MIN_TEMPORARY_CODE_POOL_SIZE) {
            for(int i = 0; i < MIN_TEMPORARY_CODE_POOL_SIZE - currentPoolSize; i++) {
                generateCode(null);
            }
        }
    }

    @Override
    public void validateTemporaryCode(String temporaryCode, long tokenId) {
        // Recupero il codice temporaneo
        var code = findByCode(temporaryCode);

        // Controllo che il codice temporaneo non sia scaduto
        if(code.isEmpty() || code.get().isExpired())
            throw new ExceptionTemporaryCode();

        // Se l'ID del token è diverso dell'ID del token della sessione
        if(!Objects.equals(code.get().getTokenId(), tokenId))
            throw new ExceptionTemporaryCode();
    }

}
