package com.candido.server.domain.v1.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemporaryCodeRepository extends JpaRepository<TemporaryCode, Integer>, JpaSpecificationExecutor<TemporaryCode> {
    Optional<TemporaryCode> findFirstByTokenId(Integer tokenId);
    Optional<TemporaryCode> findByCode(String code);
}
