package com.candido.server.service.base.geo;

import com.candido.server.domain.v1.geo.Address;
import com.candido.server.dto.v1.request.geo.RequestAddress;

import java.util.List;
import java.util.Optional;

public interface AddressService {
    List<Address> getAllActiveAddressesByUserId(Integer userId);
    Optional<Address> getAddressById(Integer addressId);
    Address getAddressByIdAndUserIdOrThrow(Integer addressId, Integer userId);
    Address saveAddress(int userId, Integer addressId, RequestAddress requestAddress);
    void deleteAddress(Integer addressId);
    Address updatePrimaryAddressForUser(int userId, Address address, Integer addressId, RequestAddress requestAddress);
}
