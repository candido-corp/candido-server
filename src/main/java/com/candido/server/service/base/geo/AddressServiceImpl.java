package com.candido.server.service.base.geo;

import com.candido.server.domain.v1.geo.Address;
import com.candido.server.domain.v1.geo.AddressRepository;
import com.candido.server.domain.v1.geo.Address_;
import com.candido.server.dto.v1.request.geo.RequestAddress;
import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.geo.ExceptionAddress;
import com.candido.server.exception.geo.ExceptionAddressNotFound;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final EntityManager entityManager;

    @Autowired
    public AddressServiceImpl(
            AddressRepository addressRepository,
            EntityManager entityManager
    ) {
        this.addressRepository = addressRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<Address> getAllActiveAddressesByUserId(Integer userId) {
        return addressRepository.findByUserIdAndDeletedAtIsNull(userId);
    }

    @Override
    public Optional<Address> getAddressById(Integer addressId) {
        return addressId != null ? addressRepository.findById(addressId) : Optional.empty();
    }

    @Override
    public Address getAddressByIdAndUserIdOrThrow(Integer addressId, Integer userId) {
        if (addressId == null || addressId == 0 || userId == null || userId == 0)
            throw new ExceptionAddressNotFound(EnumExceptionName.ERROR_BUSINESS_ADDRESS_NOT_FOUND.name());

        Specification<Address> byAddressIdAndUserId = ((root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get(Address_.ADDRESS_ID), addressId),
                        criteriaBuilder.equal(root.get(Address_.USER_ID), userId)
                ));

        return addressRepository.findOne(byAddressIdAndUserId)
                .orElseThrow(() -> new ExceptionAddressNotFound(EnumExceptionName.ERROR_BUSINESS_ADDRESS_NOT_FOUND.name()));
    }

    @Override
    @Transactional
    public Address saveAddress(int userId, Integer addressId, RequestAddress requestAddress) {
        if (requestAddress.territoryId() == null || requestAddress.addressTypeId() == null) {
            throw new ExceptionAddress(EnumExceptionName.ERROR_BUSINESS_ADDRESS_FIELDS_CAN_NOT_BE_EMPTY.name());
        }

        LocalDateTime now = LocalDateTime.now();

        Address address = getAddressById(addressId)
                .map(existing -> {
                    existing.setUpdatedAt(now);
                    return existing;
                })
                .orElseGet(() -> {
                    Address newAddress = new Address();
                    newAddress.setCreatedAt(now);
                    return newAddress;
                });

        address.setTerritoryId(requestAddress.territoryId());
        address.setAddressTypeId(requestAddress.addressTypeId());
        address.setZip(requestAddress.zip());
        address.setStreet(requestAddress.street());
        address.setHouseNumber(requestAddress.houseNumber());
        address.setUserId(userId);
        address.setIsPrimary(requestAddress.isPrimary());

        // Check if the address is primary
        address = updatePrimaryAddressForUser(userId, address, addressId, requestAddress);

        Address currentAddress = addressRepository.save(address);
        entityManager.refresh(currentAddress);
        return currentAddress;
    }


    @Override
    public void deleteAddress(Integer addressId) {
        getAddressById(addressId).ifPresent(address -> {
            if (address.getIsPrimary()) {
                throw new ExceptionAddress(EnumExceptionName.ERROR_BUSINESS_ADDRESS_CANT_DELETE_PRIMARY.name());
            }
            address.setDeletedAt(LocalDateTime.now());
            addressRepository.save(address);
        });
    }

    @Override
    public Address updatePrimaryAddressForUser(int userId, Address address, Integer addressId, RequestAddress requestAddress) {
        List<Address> userAddresses = getAllActiveAddressesByUserId(userId);

        boolean hasPrimary = userAddresses.stream().anyMatch(Address::getIsPrimary);

        if (Boolean.TRUE.equals(requestAddress.isPrimary())) {
            List<Address> addressesToUpdate = userAddresses.stream()
                    .filter(a -> Boolean.TRUE.equals(a.getIsPrimary()))
                    .peek(a -> a.setIsPrimary(false))
                    .toList();

            addressRepository.saveAll(addressesToUpdate);

            address.setIsPrimary(true);

        } else if (!hasPrimary) {
            address.setIsPrimary(true);
        } else if (addressId == null) {
            address.setIsPrimary(false);
        }

        return address;
    }

}
