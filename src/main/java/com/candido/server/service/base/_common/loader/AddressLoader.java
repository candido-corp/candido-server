package com.candido.server.service.base._common.loader;

import com.candido.server.domain.v1.geo.Address;
import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.geo.ExceptionAddressNotFound;
import com.candido.server.service.base.geo.AddressService;
import com.candido.server.validation.annotations.ownership.OwnershipLoader;
import org.springframework.stereotype.Component;

@Component
public class AddressLoader implements OwnershipLoader<Address> {

    private final AddressService addressService;

    public AddressLoader(AddressService addressService) {
        this.addressService = addressService;
    }

    @Override
    public Address loadById(Integer id) {
        return addressService.getActiveAddressById(id)
                .orElseThrow(() ->
                        new ExceptionAddressNotFound(EnumExceptionName.ERROR_BUSINESS_ADDRESS_NOT_FOUND.name())
                );
    }

}
