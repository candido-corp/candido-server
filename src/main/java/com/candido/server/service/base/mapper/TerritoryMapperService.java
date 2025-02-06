package com.candido.server.service.base.mapper;

import com.candido.server.domain.v1.geo.Territory;
import com.candido.server.dto.v1.response.geo.*;

public interface TerritoryMapperService {

    static ResponseGeoTerritoryDto mapTerritoryToGeoTerritoryDto(Territory territory) {
        return ResponseGeoTerritoryDto
                .builder()
                .territoryId(territory.getTerritoryId())
                .territoryName(territory.getTerritoryName())
                .build();
    }

}
