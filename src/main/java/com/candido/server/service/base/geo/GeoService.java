package com.candido.server.service.base.geo;

import com.candido.server.dto.v1.response.geo.GeoCountryDto;
import com.candido.server.dto.v1.response.geo.GeoMunicipalityDto;
import com.candido.server.dto.v1.response.geo.GeoProvinceDto;
import com.candido.server.dto.v1.response.geo.GeoRegionDto;

import java.util.List;

public interface GeoService {
    // Admin path
    List<GeoCountryDto> getAllCountries();
    List<GeoRegionDto> getAllRegions();
    List<GeoProvinceDto> getAllProvinces();
    List<GeoMunicipalityDto> getAllMunicipalities();

    List<GeoRegionDto> getAllRegionsByMotherRelationship(int motherRelationshipId);
    List<GeoRegionDto> getAllProvincesByMotherRelationship(int motherRelationshipId);
    List<GeoRegionDto> getAllMunicipalitiesByMotherRelationship(int motherRelationshipId);
}
