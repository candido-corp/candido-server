package com.candido.server.service.base.geo;

import com.candido.server.domain.v1.geo.Territory;

import java.util.List;

public interface TerritoryService {
    List<Territory> findAllByCategoryId(String categoryKey);
}
