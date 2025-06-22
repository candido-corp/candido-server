package com.candido.server.service.base.geo;

import com.candido.server.domain.v1.geo.Address;
import com.candido.server.domain.v1.geo.AddressRepository;
import com.candido.server.dto.v1.request.geo.RequestAddress;
import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.geo.ExceptionAddress;
import com.candido.server.exception.geo.ExceptionAddressNotFound;
import com.candido.server.service.base.geo.specifications.AddressSpecifications;
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
    public Optional<Address> findActiveAddressBySpecification(Specification<Address> specification) {
        specification = specification.and(AddressSpecifications.isActive());
        return addressRepository.findOne(specification);
    }

    @Override
    public List<Address> findAllActiveAddressBySpecification(Specification<Address> specification) {
        specification = specification.and(AddressSpecifications.isActive());
        return addressRepository.findAll(specification);
    }

    @Override
    public List<Address> getAllActiveAddressesByUserId(Integer userId) {
        return userId != null ? findAllActiveAddressBySpecification(
                AddressSpecifications.byUserId(userId)
        ) : List.of();
    }

    @Override
    public Optional<Address> getActiveAddressById(Integer addressId) {
        return addressId != null ? findActiveAddressBySpecification(
                AddressSpecifications.byId(addressId)
        ) : Optional.empty();
    }

    @Override
    public Address getActiveAddressByIdAndUserIdOrThrow(Integer addressId, Integer userId) {
        if (addressId == null || addressId == 0 || userId == null || userId == 0)
            throw new ExceptionAddressNotFound(EnumExceptionName.ERROR_BUSINESS_ADDRESS_NOT_FOUND.name());

        return findActiveAddressBySpecification(AddressSpecifications.byIdAndUserId(addressId, userId))
                .orElseThrow(() -> new ExceptionAddressNotFound(EnumExceptionName.ERROR_BUSINESS_ADDRESS_NOT_FOUND.name()));
    }

    @Override
    @Transactional
    public Address saveAddress(int userId, Integer addressId, RequestAddress request) {
        validateRequest(request);
        LocalDateTime now = LocalDateTime.now();

        Address address = addressId != null
                ? getActiveAddressById(addressId).orElseGet(this::createNew)
                : createNew();

        if (address.getCreatedAt() == null) {
            address.setCreatedAt(now);
        }
        address.setUpdatedAt(now);

        mapRequestToAddress(address, request, userId);
        enforcePrimaryConsistency(userId, address, addressId, request.isPrimary());

        return addressRepository.save(address);
    }

    @Override
    @Transactional
    public void deleteAddress(Integer addressId) {
        getActiveAddressById(addressId).ifPresent(address -> {
            boolean wasPrimary = address.getIsPrimary();
            address.setDeletedAt(LocalDateTime.now());
            address.setIsPrimary(false);
            addressRepository.save(address);

            if (wasPrimary) {
                promoteAnotherPrimary(address.getUserId());
            }
        });
    }

    /**
     * Enforces primary address consistency based on the user's existing addresses.
     *
     * @param userId    The ID of the user whose addresses are being managed.
     * @param address   The address being saved or updated.
     * @param addressId The ID of the address being updated, or null if creating a new one.
     * @param isPrimary Whether the address should be set as primary.
     */
    private void enforcePrimaryConsistency(int userId, Address address, Integer addressId, boolean isPrimary) {
        List<Address> userAddresses = getAllActiveAddressesByUserId(userId);
        boolean hasOtherPrimary = userAddresses.stream().anyMatch(Address::getIsPrimary);

        if (isPrimary) {
            demoteExistingPrimaries(userAddresses);
            address.setIsPrimary(true);
        } else if (!hasOtherPrimary && (addressId == null)) {
            address.setIsPrimary(true);
        } else {
            address.setIsPrimary(false);
        }
    }

    /**
     * Demotes all existing primary addresses to non-primary.
     *
     * @param addresses The list of addresses to check and demote if necessary.
     */
    private void demoteExistingPrimaries(List<Address> addresses) {
        List<Address> primaries = addresses.stream()
                .filter(Address::getIsPrimary)
                .peek(a -> a.setIsPrimary(false))
                .toList();

        if (!primaries.isEmpty()) {
            addressRepository.saveAll(primaries);
        }
    }

    /**
     * Promotes another address to primary if the current primary is deleted.
     *
     * @param userId The ID of the user whose addresses are being managed.
     */
    private void promoteAnotherPrimary(int userId) {
        getAllActiveAddressesByUserId(userId).stream()
                .filter(a -> !a.getIsPrimary())
                .findFirst()
                .ifPresent(a -> {
                    a.setIsPrimary(true);
                    addressRepository.save(a);
                });
    }

    /**
     * Creates a new Address entity with default values.
     *
     * @return A new Address instance.
     */
    private Address createNew() {
        return new Address();
    }

    /**
     * Validates the RequestAddress DTO to ensure required fields are present.
     *
     * @param request The RequestAddress DTO to validate.
     * @throws ExceptionAddress if any required field is missing.
     */
    private void validateRequest(RequestAddress request) {
        if (request.territoryId() == null || request.addressTypeId() == null) {
            throw new ExceptionAddress(EnumExceptionName.ERROR_BUSINESS_ADDRESS_FIELDS_CAN_NOT_BE_EMPTY.name());
        }
    }

    /**
     * Maps the RequestAddress DTO to the Address entity.
     *
     * @param address The Address entity to be populated.
     * @param request The RequestAddress DTO containing the data.
     * @param userId  The ID of the user associated with this address.
     */
    private void mapRequestToAddress(Address address, RequestAddress request, int userId) {
        address.setUserId(userId);
        address.setTerritoryId(request.territoryId());
        address.setAddressTypeId(request.addressTypeId());
        address.setStreet(request.street());
        address.setHouseNumber(request.houseNumber());
        address.setZip(request.zip());
        address.setDisplayName(request.displayName());
    }

}
