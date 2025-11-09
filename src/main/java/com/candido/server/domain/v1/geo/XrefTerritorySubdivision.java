package com.candido.server.domain.v1.geo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "xref_territory_subdivision")
@IdClass(XrefTerritorySubdivisionIdentity.class)
public class XrefTerritorySubdivision {

    @Id
    @JsonProperty("territory_mother_id")
    @Column(name = "fk_territory_mother_id")
    private int territoryMotherId;

    @Id
    @JsonProperty("territory_son_id")
    @Column(name = "fk_territory_son_id")
    private int territorySonId;

}
