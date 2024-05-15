package com.candido.server.service.base.auth.token;

import com.candido.server.domain.v1.token.TemporaryCode;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TemporaryCodeService {
    long countCodeList(Specification<TemporaryCode> specification);
    Optional<TemporaryCode> getFirstCodeNotAssigned();
    Optional<TemporaryCode> findByCode(String code);
    Optional<TemporaryCode> findByTokenId(long code);
    
    TemporaryCode generateCode(Long tokenId);

    void delete(String code);
    void delete(TemporaryCode temporaryCode);

    TemporaryCode assignCode(long tokenId);


    void checkTemporaryCodePoolSize();
}
