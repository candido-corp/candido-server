package com.candido.server.service.base.geo;

import com.candido.server.domain.v1.geo.Address;
import com.candido.server.domain.v1.geo.AddressRepository;
import com.candido.server.domain.v1.geo.AddressType;
import com.candido.server.dto.v1.request.geo.RequestAddress;
import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.geo.ExceptionAddress;
import com.candido.server.exception.geo.ExceptionAddressNotFound;
import com.candido.server.exception.geo.ExceptionAddressTypeNotFound;
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
    private final GeoAddressTypeService geoAddressTypeService;

    @Autowired
    public AddressServiceImpl(
            AddressRepository addressRepository,
            EntityManager entityManager,
            GeoAddressTypeService geoAddressTypeService
    ) {
        this.addressRepository = addressRepository;
        this.entityManager = entityManager;
        this.geoAddressTypeService = geoAddressTypeService;
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
    public List<Address> getAllActiveAddressesByUserId(Long userId) {
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
    public Address getActiveAddressByIdAndUserIdOrThrow(Integer addressId, Long userId) {
        if (addressId == null || addressId == 0 || userId == null || userId == 0)
            throw new ExceptionAddressNotFound(EnumExceptionName.ERROR_BUSINESS_ADDRESS_NOT_FOUND.name());

        return findActiveAddressBySpecification(AddressSpecifications.byIdAndUserId(addressId, userId))
                .orElseThrow(() -> new ExceptionAddressNotFound(EnumExceptionName.ERROR_BUSINESS_ADDRESS_NOT_FOUND.name()));
    }

    @Override
    @Transactional
    public Address saveAddress(Long userId, Integer addressId, RequestAddress request) {
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

        return address;
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

    @Override
    public Address setPrimaryAddress(Long userId, Integer addressId) {
        Address address = getActiveAddressById(addressId).orElseThrow(() -> new ExceptionAddressNotFound(
                EnumExceptionName.ERROR_BUSINESS_ADDRESS_NOT_FOUND.name()
        ));

        LocalDateTime now = LocalDateTime.now();
        address.setUpdatedAt(now);
        enforcePrimaryConsistency(userId, address, addressId, true);

        return address;
    }

    /**
     * Enforces primary address consistency based on the user's existing addresses.
     *
     * @param userId    The ID of the user whose addresses are being managed.
     * @param address   The address being saved or updated.
     * @param addressId The ID of the address being updated, or null if creating a new one.
     * @param isPrimary Whether the address should be set as primary.
     */
    private void enforcePrimaryConsistency(Long userId, Address address, Integer addressId, boolean isPrimary) {
        List<Address> userAddresses = getAllActiveAddressesByUserId(userId);
        boolean hasOtherPrimary = userAddresses.stream()
                .filter(a -> addressId == null || a.getAddressId() != addressId)
                .anyMatch(Address::getIsPrimary);

        if (isPrimary) {
            demoteExistingPrimaries(userAddresses);
            address.setIsPrimary(true);
        } else if (!hasOtherPrimary) {
            address.setIsPrimary(true);
        } else {
            address.setIsPrimary(false);
        }

        addressRepository.save(address);
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
    private void promoteAnotherPrimary(Long userId) {
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
    private void mapRequestToAddress(Address address, RequestAddress request, Long userId) {
        address.setUserId(userId);
        address.setTerritoryId(request.territoryId());
        address.setStreet(request.street());
        address.setHouseNumber(request.houseNumber());
        address.setZip(request.zip());
        address.setDisplayName(request.displayName());

        AddressType addressType = geoAddressTypeService
                .findById(request.addressTypeId())
                .orElseThrow(() -> new ExceptionAddressTypeNotFound(
                        EnumExceptionName.ERROR_BUSINESS_ADDRESS_TYPE_NOT_FOUND.name()
                ));
        address.setAddressType(addressType);
        address.setAddressTypeId(addressType.getAddressTypeId());
    }

}
