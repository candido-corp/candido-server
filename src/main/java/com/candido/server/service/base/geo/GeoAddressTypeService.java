package com.candido.server.service.base.geo;

import com.candido.server.domain.v1.geo.AddressType;

import java.util.List;

public interface GeoAddressTypeService {
    List<AddressType> findAll();
}
