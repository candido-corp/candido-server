package com.candido.server.service.base.mapper;

import com.candido.server.domain.v1.geo.Address;
import com.candido.server.domain.v1.geo.AddressType;
import com.candido.server.dto.v1.response.geo.ResponseAddressType;
import com.candido.server.dto.v1.response.geo.ResponseUserAddress;

public interface AddressMapperService {
    ResponseUserAddress addressToUserAddressDto(Address address);
    ResponseAddressType addressTypeToAddressTypeDto(AddressType address);
}
