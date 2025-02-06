package com.candido.server.dto.v1.response.geo;

import com.candido.server.domain.v1.geo.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ResponseUserAddress {

    @JsonProperty("address_id")
    private int addressId;

    @JsonProperty("type_id")
    private int addressTypeId;

    @JsonProperty("territories")
    private List<ResponseUserAddressTerritory> territories;

    @JsonProperty("type")
    private String type;

    @JsonProperty("zip")
    private String zip;

    @JsonProperty("street")
    private String street;

    @JsonProperty("house_number")
    private String houseNumber;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public ResponseUserAddress(Address address) {
        this.addressId = address.getAddressId();
        this.addressTypeId = address.getAddressTypeId();
        this.zip = address.getZip();
        this.street = address.getStreet();
        this.houseNumber = address.getHouseNumber();
        this.updatedAt = address.getUpdatedAt();
        this.type = address.getAddressType().getDescription();
    }
}
