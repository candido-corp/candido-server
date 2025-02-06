package com.candido.server.service.base.mapper;

import com.candido.server.domain.v1.geo.Address;
import com.candido.server.dto.v1.response.geo.ResponseUserAddress;
import com.candido.server.service.base.geo.TerritoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressMapperServiceImpl implements AddressMapperService {

    private final TerritoryService territoryService;

    @Autowired
    public AddressMapperServiceImpl(
            TerritoryService territoryService
    ) {
        this.territoryService = territoryService;
    }

    @Override
    public ResponseUserAddress addressToUserAddressDto(Address address) {
        if (address == null) return null;
        ResponseUserAddress responseUserAddress = new ResponseUserAddress(address);
        responseUserAddress.setTerritories(territoryService.getBranchTerritories(address.getTerritoryId()));
        return responseUserAddress;
    }

}
