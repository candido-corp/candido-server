package com.candido.server.service.base.geo;

import com.candido.server.domain.v1.geo.TerritoryCategory;

import java.util.Optional;

public interface TerritoryCategoryService {
    Optional<TerritoryCategory> findByCategoryKey(String categoryKey);
}
