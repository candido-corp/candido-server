package com.candido.server.service.base.auth.token;

import com.candido.server.config.ConfigTemporaryCode;
import com.candido.server.domain.v1.token.TemporaryCode;
import com.candido.server.domain.v1.token.TemporaryCodeRepository;
import com.candido.server.domain.v1.token.TemporaryCode_;
import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.security.auth.ExceptionTemporaryCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class TemporaryCodeServiceImpl implements TemporaryCodeService {

    private final TemporaryCodeRepository temporaryCodeRepository;

    private final TemporaryCodePoolService temporaryCodePoolService;

    @Autowired
    public TemporaryCodeServiceImpl(
            TemporaryCodeRepository temporaryCodeRepository,
            TemporaryCodePoolService temporaryCodePoolService
    ) {
        this.temporaryCodeRepository = temporaryCodeRepository;
        this.temporaryCodePoolService = temporaryCodePoolService;
    }

    @Override
    public Optional<TemporaryCode> getFirstCodeNotAssigned() {
        return temporaryCodePoolService.isTemporaryCodePoolEmpty() ?
                Optional.empty() : temporaryCodeRepository.findFirstByTokenId(null);
    }

    @Override
    public Optional<TemporaryCode> findByCode(String code) {
        return temporaryCodeRepository.findByCode(code);
    }

    @Override
    public Optional<TemporaryCode> findByTokenId(long tokenId) {
        Specification<TemporaryCode> byTokenId = ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(TemporaryCode_.TOKEN_ID), tokenId));
        return temporaryCodeRepository.findOne(byTokenId);
    }

    @Override
    public void deleteTemporaryCodeByCodeString(String code) {
        findByCode(code).ifPresent(temporaryCodeRepository::delete);
    }

    @Override
    public void deleteTemporaryCodeByTokenId(long tokenId) {
        findByTokenId(tokenId).ifPresent(temporaryCodeRepository::delete);
    }

    @Override
    public void deleteTemporaryCodeInstance(TemporaryCode temporaryCode) {
        temporaryCodeRepository.delete(temporaryCode);
    }

    @Override
    public TemporaryCode assignCode(long tokenId) {
        TemporaryCode temporaryCode = getFirstCodeNotAssigned()
                .orElseGet(() -> temporaryCodePoolService.generateCode(tokenId));

        if(temporaryCode.getTokenId() == null) {
            temporaryCode.setTokenId(tokenId);
            temporaryCode.setExpirationDate(LocalDateTime.now().plusSeconds(ConfigTemporaryCode.MAX_TEMPORARY_CODE_DURATION));
            temporaryCodeRepository.save(temporaryCode);
        }

        temporaryCodePoolService.checkTemporaryCodePoolSize();
        return temporaryCode;
    }

    @Override
    public void validateTemporaryCode(String temporaryCode, long tokenId) {
        var code = findByCode(temporaryCode);

        var isCodePresent = code.isPresent();
        var isCodeExpired = isCodePresent && code.get().isExpired();
        var isCodeAssignedToToken = isCodePresent && Objects.equals(code.get().getTokenId(), tokenId);

        if(!isCodePresent || isCodeExpired || !isCodeAssignedToToken)
            throw new ExceptionTemporaryCode(EnumExceptionName.ERROR_VALIDATION_INVALID_TEMPORARY_CODE.name());
    }

}
