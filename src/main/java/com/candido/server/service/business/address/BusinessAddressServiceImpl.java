package com.candido.server.service.business.address;

import com.candido.server.domain.v1.geo.Address;
import com.candido.server.domain.v1.user.User;
import com.candido.server.dto.v1.request.geo.RequestAddress;
import com.candido.server.dto.v1.response.geo.ResponseUserAddress;
import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.geo.ExceptionAddressNotFound;
import com.candido.server.service.base._common.loader.AddressLoader;
import com.candido.server.service.base.geo.AddressService;
import com.candido.server.service.base.mapper.AddressMapperService;
import com.candido.server.service.base.user.UserService;
import com.candido.server.validation.annotations.ownership.CheckOwnership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessAddressServiceImpl implements BusinessAddressService {

    private final AddressService addressService;
    private final UserService userService;
    private final AddressMapperService addressMapper;

    @Autowired
    public BusinessAddressServiceImpl(
            AddressService addressService,
            UserService userService,
            AddressMapperService addressMapper
    ) {
        this.addressService = addressService;
        this.userService = userService;
        this.addressMapper = addressMapper;
    }

    @Override
    public List<ResponseUserAddress> getUserAddresses(Authentication authentication) {
        User authenticatedUser = userService.getAuthenticatedUser(authentication);
        return addressService.getAllActiveAddressesByUserId(authenticatedUser.getId())
                .stream()
                .map(addressMapper::addressToUserAddressDto)
                .toList();
    }

    @Override
    @CheckOwnership(idParam = "addressId", loader = AddressLoader.class)
    public ResponseUserAddress getUserAddressById(int addressId) {
        return addressService.getActiveAddressById(addressId)
                .stream()
                .map(addressMapper::addressToUserAddressDto)
                .findFirst()
                .orElseThrow(() -> new ExceptionAddressNotFound(EnumExceptionName.ERROR_BUSINESS_ADDRESS_NOT_FOUND.name()));
    }

    @Override
    public ResponseUserAddress createUserAddress(Authentication authentication, RequestAddress requestUserAddressDto) {
        User authenticatedUser = userService.getAuthenticatedUser(authentication);
        Address address = addressService.saveAddress(authenticatedUser.getId(), null, requestUserAddressDto);
        if (address == null)
            throw new ExceptionAddressNotFound(EnumExceptionName.ERROR_BUSINESS_ADDRESS_NOT_FOUND.name());
        return addressMapper.addressToUserAddressDto(address);
    }

    @Override
    @CheckOwnership(idParam = "addressId", loader = AddressLoader.class)
    public ResponseUserAddress updateUserAddress(Authentication authentication, RequestAddress requestUserAddressDto, int addressId) {
        User authenticatedUser = userService.getAuthenticatedUser(authentication);
        Address address = addressService.saveAddress(authenticatedUser.getId(), addressId, requestUserAddressDto);
        if (address == null)
            throw new ExceptionAddressNotFound(EnumExceptionName.ERROR_BUSINESS_ADDRESS_NOT_FOUND.name());
        return addressMapper.addressToUserAddressDto(address);
    }

    @Override
    @CheckOwnership(idParam = "addressId", loader = AddressLoader.class)
    public void deleteAddress(Integer addressId) {
        addressService.deleteAddress(addressId);
    }
}
