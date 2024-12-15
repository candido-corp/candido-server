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
@Table(name = "territory")
public class Territory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("territory_id")
    @Column(name = "territory_id")
    private int territoryId;

    @ManyToOne
    @JoinColumn(name = "fk_territory_category_id", insertable = false, updatable = false)
    private TerritoryCategory territoryCategory;

    @JsonProperty("territory_category_id")
    @Column(name = "fk_territory_category_id")
    private int territoryCategoryId;

    @JsonProperty("territory_name")
    @Column(name = "territory_name")
    private String territoryName;
}
