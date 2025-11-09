package com.candido.server.service.base.auth.token;

import com.candido.server.domain.v1.token.TemporaryCode;
import org.springframework.data.jpa.domain.Specification;

public interface TemporaryCodePoolService {
    long countCodeList(Specification<TemporaryCode> specification);
    boolean isTemporaryCodePoolEmpty();
    void checkTemporaryCodePoolSize();
    TemporaryCode generateCode(Long tokenId);
}
