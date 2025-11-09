package com.candido.server.domain.v1.geo;

import lombok.Data;

import java.io.Serializable;

@Data
public class XrefTerritorySubdivisionIdentity implements Serializable {
    int territoryMotherId;
    int territorySonId;
}
