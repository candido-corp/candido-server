package com.candido.server.controller.geo;

import com.candido.server.dto.v1.response.geo.GeoCountryDto;
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

    @GetMapping("/countries")
    public ResponseEntity<List<GeoCountryDto>> getAllCountries() {
        return ResponseEntity.ok(geoService.getAllCountries());
    }

}

