package com.candido.server.domain.v1.geo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface XrefTerritorySubdivisionRepository extends JpaRepository<XrefTerritorySubdivision, Integer>,
        JpaSpecificationExecutor<XrefTerritorySubdivision> {
}
