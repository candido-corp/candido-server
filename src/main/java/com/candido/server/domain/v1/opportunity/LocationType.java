package com.candido.server.domain.v1.opportunity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "location_type", schema = "candido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_type_id")
    private Long locationTypeId;

    @Column(name = "key", unique = true, nullable = false)
    private String key;

    @Column(name = "label")
    private String label;
}
