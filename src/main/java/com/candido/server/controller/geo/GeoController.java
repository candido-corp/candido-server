package com.candido.server.controller.geo;

import com.candido.server.domain.v1.geo.EnumTerritoryCategoryKey;
import com.candido.server.dto.v1.response.geo.ResponseAddressType;
import com.candido.server.dto.v1.response.geo.ResponseGeoTerritory;
import com.candido.server.dto.v1.response.geo.ResponseGeoTerritoryList;
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
    public ResponseEntity<List<ResponseGeoTerritory>> getAllCountries() {
        return ResponseEntity.ok(geoService.getTerritories(EnumTerritoryCategoryKey.T_COUNTRY.name()));
    }

    @GetMapping("/{territoryId}/children")
    public ResponseEntity<ResponseGeoTerritoryList> getAllChildrenByTerritoryId(
            @PathVariable("territoryId") int territoryId
    ) {
        ResponseGeoTerritoryList territoryList = geoService.getTerritoryChildren(territoryId);
        return ResponseEntity.ok(territoryList);
    }

}

