package com.candido.server.service.base.geo;

import com.candido.server.domain.v1.geo.XrefTerritorySubdivision;
import com.candido.server.domain.v1.geo.XrefTerritorySubdivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class XrefTerritorySubdivisionServiceImpl implements XrefTerritorySubdivisionService {

    XrefTerritorySubdivisionRepository xrefTerritorySubdivisionRepository;

    @Autowired
    private XrefTerritorySubdivisionServiceImpl(
            XrefTerritorySubdivisionRepository xrefTerritorySubdivisionRepository
    ) {
        this.xrefTerritorySubdivisionRepository = xrefTerritorySubdivisionRepository;
    }

    @Override
    public Optional<XrefTerritorySubdivision> findByTerritoryMotherId() {
       // Specification<XrefTerritorySubdivision> byMotherRelationshipId = (root, query, criteriaBuilder) ->
       //         criteriaBuilder.equal(XrefTerrit)
        return Optional.empty();
    }

    @Override
    public Optional<XrefTerritorySubdivision> findByTerritorySonId() {
        return Optional.empty();
    }
}
