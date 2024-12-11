package com.candido.server.service.base.geo;

import com.candido.server.domain.v1.geo.XrefTerritorySubdivision;

import java.util.List;
import java.util.Optional;

public interface XrefTerritorySubdivisionService {
    List<XrefTerritorySubdivision> findByTerritoryMotherId(int motherId);
    Optional<XrefTerritorySubdivision> findByTerritorySonId(int sonId);
}
