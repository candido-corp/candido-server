package com.candido.server.service.base.geo;

import com.candido.server.domain.v1.geo.Address;
import com.candido.server.dto.v1.request.geo.RequestAddress;

import java.util.Optional;

public interface AddressService {
    Optional<Address> getAddressById(Integer addressId);
    Address saveAddress(Integer addressId, RequestAddress requestAddress);
    void deleteAddress(Integer addressId);
}
