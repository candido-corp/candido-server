package com.candido.server.service.auth.token;

import com.candido.server.domain.v1.token.TemporaryCode;

import java.util.Optional;

public interface TemporaryCodeService {
    Optional<TemporaryCode> findByCode(String code);
    TemporaryCode generateCode();
    void delete(String code);
}
