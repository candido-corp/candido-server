package com.candido.server.service.business.address;

import com.candido.server.dto.v1.request.geo.RequestAddress;
import com.candido.server.dto.v1.response.geo.ResponseUserAddress;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface BusinessAddressService {
    List<ResponseUserAddress> getUserAddresses(Authentication authentication);
    ResponseUserAddress getUserAddressById(int addressId);
    ResponseUserAddress createUserAddress(Authentication authentication, RequestAddress requestUserAddressDto);
    ResponseUserAddress updateUserAddress(Authentication authentication, RequestAddress requestUserAddressDto, int addressId);
    void deleteAddress(Integer addressId);
}
