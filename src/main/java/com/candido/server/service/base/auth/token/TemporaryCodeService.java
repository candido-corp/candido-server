package com.candido.server.service.base.auth.token;

import com.candido.server.domain.v1.token.TemporaryCode;

import java.util.Optional;

public interface TemporaryCodeService {
    Optional<TemporaryCode> getFirstCodeNotAssigned();
    Optional<TemporaryCode> findByCode(String code);
    Optional<TemporaryCode> findByTokenId(long code);

    void deleteTemporaryCodeByCodeString(String code);
    void deleteTemporaryCodeByTokenId(long tokenId);
    void deleteTemporaryCodeInstance(TemporaryCode temporaryCode);

    TemporaryCode assignCode(long tokenId);

    void validateTemporaryCode(String temporaryCode, long tokenId);
}
