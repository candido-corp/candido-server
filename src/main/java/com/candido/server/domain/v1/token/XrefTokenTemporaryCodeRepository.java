package com.candido.server.domain.v1.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface XrefTokenTemporaryCodeRepository extends JpaRepository<XrefTokenTemporaryCode, Integer> {
}
