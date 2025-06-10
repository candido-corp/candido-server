package com.candido.server.service.base.geo;

import com.candido.server.domain.v1.geo.Address;
import com.candido.server.dto.v1.request.geo.RequestAddress;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface AddressService {
    Optional<Address> findActiveAddressBySpecification(Specification<Address> specification);
    List<Address> findAllActiveAddressBySpecification(Specification<Address> specification);
    List<Address> getAllActiveAddressesByUserId(Integer userId);
    Optional<Address> getActiveAddressById(Integer addressId);
    Address getActiveAddressByIdAndUserIdOrThrow(Integer addressId, Integer userId);
    Address saveAddress(int userId, Integer addressId, RequestAddress requestAddress);
    void deleteAddress(Integer addressId);
}
