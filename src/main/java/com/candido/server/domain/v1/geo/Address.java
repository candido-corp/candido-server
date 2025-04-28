package com.candido.server.domain.v1.geo;

import com.candido.server.domain.v1.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("address_id")
    @Column(name = "address_id")
    private int addressId;

    @OneToOne
    @JoinColumn(name = "fk_territory_id", insertable = false, updatable = false)
    private Territory territory;

    @JsonProperty("territory_id")
    @Column(name = "fk_territory_id")
    private int territoryId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_address_type_id", insertable = false, updatable = false)
    private AddressType addressType;

    @JsonProperty("type_id")
    @Column(name = "fk_address_type_id")
    private int addressTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", nullable = false, insertable = false, updatable = false)
    private User user;

    @JsonProperty("user_id")
    @Column(name = "fk_user_id")
    private int userId;

    @JsonProperty("is_primary")
    @Column(name = "is_primary")
    private Boolean isPrimary;

    @JsonProperty("zip")
    @Column(name = "zip")
    private String zip;

    @JsonProperty("street")
    @Column(name = "street")
    private String street;

    @JsonProperty("house_number")
    @Column(name = "house_number")
    private String houseNumber;

    @JsonProperty("created_at")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("deleted_at")
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
