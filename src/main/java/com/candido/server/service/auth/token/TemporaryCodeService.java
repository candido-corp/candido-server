package com.candido.server.service.auth.token;

import com.candido.server.domain.v1.token.TemporaryCode;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TemporaryCodeService {
    long countCodeList(Specification<TemporaryCode> specification);
    Optional<TemporaryCode> getFirstCodeNotAssigned();
    Optional<TemporaryCode> findByCode(String code);
    TemporaryCode generateCode(Integer tokenId, LocalDateTime expirationDate);
    void checkTemporaryCodePoolSize();
    void delete(String code);
    TemporaryCode assignCode(int tokenId);
}
