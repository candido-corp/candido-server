package com.candido.server.domain.v1.geo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address_type")
public class AddressType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("address_type_id")
    @Column(name = "address_type_id")
    private int addressTypeId;

    @JsonProperty("description")
    @Column(name = "description")
    private String description;

}
