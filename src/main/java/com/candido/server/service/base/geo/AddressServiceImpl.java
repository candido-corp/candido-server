package com.candido.server.service.base.geo;

import com.candido.server.domain.v1.geo.Address;
import com.candido.server.domain.v1.geo.AddressRepository;
import com.candido.server.dto.v1.request.geo.RequestAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(
            AddressRepository addressRepository
    ) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Optional<Address> getAddressById(Integer addressId) {
        return addressId != null ? addressRepository.findById(addressId) : Optional.empty();
    }

    @Override
    public Address saveAddress(Integer addressId, RequestAddress requestAddress) {
        if(requestAddress.territoryId() == null || requestAddress.addressTypeId() == null) {
            return null;
        }

        Optional<Address> optionalAddress = getAddressById(addressId);
        Address address = new Address();

        if (optionalAddress.isPresent()) {
            address = optionalAddress.get();
            address.setUpdatedAt(LocalDateTime.now());
        } else {
            address.setCreatedAt(LocalDateTime.now());
        }

        address.setTerritoryId(requestAddress.territoryId());
        address.setAddressTypeId(requestAddress.addressTypeId());
        address.setZip(requestAddress.zip());
        address.setStreet(requestAddress.street());
        address.setHouseNumber(requestAddress.houseNumber());

        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Integer addressId) {
        getAddressById(addressId).ifPresent(address -> {
            address.setDeletedAt(LocalDateTime.now());
            addressRepository.save(address);
        });
    }

}
