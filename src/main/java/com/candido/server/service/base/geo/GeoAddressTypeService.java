package com.candido.server.service.base.geo;

import com.candido.server.domain.v1.geo.AddressType;

import java.util.List;
import java.util.Optional;

public interface GeoAddressTypeService {
    Optional<AddressType> findById(int id);
    List<AddressType> findAll();
}
