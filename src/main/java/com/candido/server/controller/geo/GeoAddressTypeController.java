package com.candido.server.controller.geo;

import com.candido.server.dto.v1.response.geo.ResponseAddressType;
import com.candido.server.service.base.geo.GeoAddressTypeService;
import com.candido.server.service.base.mapper.AddressMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/geos/address-types")
public class GeoAddressTypeController {

    private final GeoAddressTypeService geoAddressTypeService;
    private final AddressMapperService addressMapperService;

    @Autowired
    public GeoAddressTypeController(
            GeoAddressTypeService geoAddressTypeService,
            AddressMapperService addressMapperService
    ) {
        this.geoAddressTypeService = geoAddressTypeService;
        this.addressMapperService = addressMapperService;
    }

    @GetMapping
    public ResponseEntity<List<ResponseAddressType>> getAllAddressType() {
        List<ResponseAddressType> addressTypes = geoAddressTypeService.findAll()
                .stream()
                .map(addressMapperService::addressTypeToAddressTypeDto)
                .toList();
        return ResponseEntity.ok(addressTypes);
    }

}
