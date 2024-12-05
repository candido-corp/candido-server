package com.candido.server.service.base.mapper;

import com.candido.server.domain.v1.geo.Territory;
import com.candido.server.dto.v1.response.geo.GeoCountryDto;
import com.candido.server.dto.v1.response.geo.GeoMunicipalityDto;
import com.candido.server.dto.v1.response.geo.GeoProvinceDto;
import com.candido.server.dto.v1.response.geo.GeoRegionDto;

public interface TerritoryMapperService {

    static GeoCountryDto mapTerritoryToGeoCountryDto(Territory territory) {
        return GeoCountryDto
                .builder()
                .countryId(territory.getTerritoryId())
                .countryName(territory.getTerritoryName())
                .build();
    }

    static GeoRegionDto mapTerritoryToGeoRegionDto(Territory territory) {
        return GeoRegionDto
                .builder()
                .regionId(territory.getTerritoryId())
                .regionName(territory.getTerritoryName())
                .build();
    }

    static GeoProvinceDto mapTerritoryToGeoProvinceDto(Territory territory) {
        return GeoProvinceDto
                .builder()
                .provinceId(territory.getTerritoryId())
                .provinceName(territory.getTerritoryName())
                .build();
    }

    static GeoMunicipalityDto mapTerritoryToGeoMunicipalityDto(Territory territory) {
        return GeoMunicipalityDto
                .builder()
                .municipalityId(territory.getTerritoryId())
                .municipalityName(territory.getTerritoryName())
                .build();
    }
}
