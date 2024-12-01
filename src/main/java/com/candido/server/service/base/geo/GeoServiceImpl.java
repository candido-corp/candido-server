package com.candido.server.service.base.geo;

import com.candido.server.domain.v1.geo.*;
import com.candido.server.dto.v1.response.geo.GeoCountryDto;
import com.candido.server.dto.v1.response.geo.GeoRegionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeoServiceImpl implements GeoService {

    private final TerritoryService territoryService;

    @Autowired
    GeoServiceImpl(
            TerritoryService territoryService
    ) {
        this.territoryService = territoryService;
    }

    @Override
    public List<GeoCountryDto> getAllCountries() {
        List<Territory> countryTerritories = territoryService.findAllByCategoryId(EnumTerritoryCategoryKey.T_COUNTRY.name());
        return countryTerritories
                .stream()
                .map(territory ->
                        GeoCountryDto
                                .builder()
                                .countryId(territory.getTerritoryId())
                                .countryName(territory.getTerritoryName())
                                .build()
                ).toList();
    }

    @Override
    public List<GeoRegionDto> getAllRegions() {
        List<Territory> regionTerritories = territoryService.findAllByCategoryId(EnumTerritoryCategoryKey.T_REGION.name());
        return regionTerritories
                .stream()
                .map(territory ->
                        GeoRegionDto
                                .builder()
                                .regionId(territory.getTerritoryId())
                                .regionName(territory.getTerritoryName())
                                .build()
                ).toList();
    }
}
