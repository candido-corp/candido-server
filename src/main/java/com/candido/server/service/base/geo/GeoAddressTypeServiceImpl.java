package com.candido.server.service.base.geo;

import com.candido.server.domain.v1.geo.AddressType;
import com.candido.server.domain.v1.geo.AddressTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeoAddressTypeServiceImpl implements GeoAddressTypeService {

    private final AddressTypeRepository addressTypeRepository;

    @Autowired
    public GeoAddressTypeServiceImpl(AddressTypeRepository addressTypeRepository) {
        this.addressTypeRepository = addressTypeRepository;
    }

    @Override
    public List<AddressType> findAll() {
        return addressTypeRepository.findAll();
    }
}
