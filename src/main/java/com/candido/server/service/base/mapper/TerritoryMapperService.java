package com.candido.server.service.base.mapper;

import com.candido.server.domain.v1.geo.Territory;
import com.candido.server.dto.v1.response.geo.*;

public interface TerritoryMapperService {

    static GeoTerritoryDto mapTerritoryToGeoTerritoryDto(Territory territory) {
        return GeoTerritoryDto
                .builder()
                .territoryId(territory.getTerritoryId())
                .territoryName(territory.getTerritoryName())
                .build();
    }

}
