package com.candido.server.service.base.geo;

import com.candido.server.dto.v1.response.geo.*;

import java.util.List;

public interface GeoService {
    List<GeoTerritoryDto> getTerritories(String territoryKey);
    GeoTerritoryListDto getTerritoryChildren(int territoryId);
}
