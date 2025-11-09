package com.candido.server.service.base.geo;

import com.candido.server.domain.v1.geo.Territory;
import com.candido.server.dto.v1.response.geo.ResponseUserAddressTerritory;

import java.util.List;
import java.util.Optional;

public interface TerritoryService {
    Optional<Territory> findById(int territoryId);
    List<Territory> findAllTerritoriesBySonListId(List<Integer> sonListId);
    List<Territory> findAllByCategoryId(String categoryKey);
    List<ResponseUserAddressTerritory> getBranchTerritories(int territoryId);
}
