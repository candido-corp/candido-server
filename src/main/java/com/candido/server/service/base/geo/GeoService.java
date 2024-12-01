package com.candido.server.service.base.geo;

import com.candido.server.dto.v1.response.geo.GeoCountryDto;
import com.candido.server.dto.v1.response.geo.GeoRegionDto;

import java.util.List;

public interface GeoService {
    List<GeoCountryDto> getAllCountries();
    List<GeoRegionDto> getAllRegions();
}
