package com.candido.server.service.base.geo;

import com.candido.server.domain.v1.geo.*;
import com.candido.server.dto.v1.response.geo.GeoCountryDto;
import com.candido.server.dto.v1.response.geo.GeoMunicipalityDto;
import com.candido.server.dto.v1.response.geo.GeoProvinceDto;
import com.candido.server.dto.v1.response.geo.GeoRegionDto;
import com.candido.server.service.base.mapper.TerritoryMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class GeoServiceImpl implements GeoService {

    private final TerritoryService territoryService;

    @Autowired
    GeoServiceImpl(
            TerritoryService territoryService
    ) {
        this.territoryService = territoryService;
    }

    private <T> List<T> getTerritories(String territoryKey, Function<? super Territory, ? extends T> mapper) {
        return territoryService.findAllByCategoryId(territoryKey)
                .stream()
                .map(mapper)
                .<T>map(x -> x)
                .toList();
    }

    @Override
    public List<GeoCountryDto> getAllCountries() {
        return getTerritories(
                EnumTerritoryCategoryKey.T_COUNTRY.name(),
                TerritoryMapperService::mapTerritoryToGeoCountryDto
        );
    }

    @Override
    public List<GeoRegionDto> getAllRegions() {
        return getTerritories(
                EnumTerritoryCategoryKey.T_REGION.name(),
                TerritoryMapperService::mapTerritoryToGeoRegionDto
        );
    }

    @Override
    public List<GeoProvinceDto> getAllProvinces() {
        return getTerritories(
                EnumTerritoryCategoryKey.T_PROVINCE.name(),
                TerritoryMapperService::mapTerritoryToGeoProvinceDto
        );
    }

    @Override
    public List<GeoMunicipalityDto> getAllMunicipalities() {
        return getTerritories(
                EnumTerritoryCategoryKey.T_MUNICIPALITY.name(),
                TerritoryMapperService::mapTerritoryToGeoMunicipalityDto
        );
    }

    @Override
    public List<GeoRegionDto> getAllRegionsByMotherRelationship(int motherRelationshipId) {
        return List.of();
    }

    @Override
    public List<GeoRegionDto> getAllProvincesByMotherRelationship(int motherRelationshipId) {
        return List.of();
    }

    @Override
    public List<GeoRegionDto> getAllMunicipalitiesByMotherRelationship(int motherRelationshipId) {
        return List.of();
    }
}
