package com.candido.server.service.base.geo;

import com.candido.server.domain.v1.geo.XrefTerritorySubdivision;

import java.util.Optional;

public interface XrefTerritorySubdivisionService {
    Optional<XrefTerritorySubdivision> findByTerritoryMotherId();
    Optional<XrefTerritorySubdivision> findByTerritorySonId();
}
