package com.candido.server.service.base.geo;

import com.candido.server.domain.v1.geo.XrefTerritorySubdivision;
import com.candido.server.domain.v1.geo.XrefTerritorySubdivisionRepository;
import com.candido.server.domain.v1.geo.XrefTerritorySubdivision_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<XrefTerritorySubdivision> findByTerritoryMotherId(int motherId) {
        Specification<XrefTerritorySubdivision> byTerritoryId = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(XrefTerritorySubdivision_.TERRITORY_MOTHER_ID), motherId);
        return xrefTerritorySubdivisionRepository.findAll(byTerritoryId);
    }

    @Override
    public Optional<XrefTerritorySubdivision> findByTerritorySonId(int sonId) {
        Specification<XrefTerritorySubdivision> byTerritoryId = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(XrefTerritorySubdivision_.TERRITORY_SON_ID), sonId);
        return xrefTerritorySubdivisionRepository.findOne(byTerritoryId);
    }
}
