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
@Table(name = "territory_category")
public class TerritoryCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("territory_category_id")
    @Column(name = "territory_category_id")
    private int territoryCategoryId;

    @JsonProperty("territory_category_key")
    @Column(name = "territory_category_key")
    private String territoryCategoryKey;

    @JsonProperty("description")
    @Column(name = "description")
    private String description;
}
