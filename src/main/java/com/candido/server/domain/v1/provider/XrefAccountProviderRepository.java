package com.candido.server.domain.v1.provider;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface XrefAccountProviderRepository extends JpaRepository<XrefAccountProvider, Integer> {
}
