package com.candido.server.controller.geo;

import com.candido.server.domain.v1.geo.EnumTerritoryCategoryKey;
import com.candido.server.dto.v1.response.geo.ResponseGeoTerritoryDto;
import com.candido.server.dto.v1.response.geo.ResponseGeoTerritoryListDto;
import com.candido.server.service.base.geo.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/geos")
public class GeoController {

    private final GeoService geoService;

    @Autowired
    public GeoController(
            GeoService geoService
    ) {
        this.geoService = geoService;
    }

    @GetMapping
    public ResponseEntity<List<ResponseGeoTerritoryDto>> getAllCountries() {
        return ResponseEntity.ok(geoService.getTerritories(EnumTerritoryCategoryKey.T_COUNTRY.name()));
    }

    @GetMapping("/{territoryId}/children")
    public ResponseEntity<ResponseGeoTerritoryListDto> getAllChildrenByTerritoryId(
            @PathVariable("territoryId") int territoryId
    ) {
        ResponseGeoTerritoryListDto territoryList = geoService.getTerritoryChildren(territoryId);
        return ResponseEntity.ok(territoryList);
    }

}

