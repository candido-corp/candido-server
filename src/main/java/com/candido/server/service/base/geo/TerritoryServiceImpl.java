package com.candido.server.service.base.geo;

import com.candido.server.domain.v1.geo.*;
import com.candido.server.dto.v1.response.geo.ResponseGeoTerritory;
import com.candido.server.dto.v1.response.geo.ResponseUserAddressTerritory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TerritoryServiceImpl implements TerritoryService {

    private final TerritoryRepository territoryRepository;
    private final TerritoryCategoryService territoryCategoryService;
    private final XrefTerritorySubdivisionService xrefTerritorySubdivisionService;

    @Autowired
    TerritoryServiceImpl(
            TerritoryRepository territoryRepository,
            TerritoryCategoryService territoryCategoryService,
            XrefTerritorySubdivisionService xrefTerritorySubdivisionService
    ) {
        this.territoryRepository = territoryRepository;
        this.territoryCategoryService = territoryCategoryService;
        this.xrefTerritorySubdivisionService = xrefTerritorySubdivisionService;
    }

    @Override
    public Optional<Territory> findById(int territoryId) {
        Specification<Territory> byTerritoryId = (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get(Territory_.TERRITORY_ID), territoryId);

        return territoryRepository.findOne(byTerritoryId);
    }

    @Override
    public List<Territory> findAllTerritoriesBySonListId(List<Integer> sonListId) {
        if (sonListId == null || sonListId.isEmpty()) {
            return List.of();
        }

        Specification<Territory> bySonListId = (root, query, criteriaBuilder) ->
                root.get(Territory_.TERRITORY_ID).in(sonListId);

        return territoryRepository.findAll(bySonListId);
    }

    @Override
    public List<Territory> findAllByCategoryId(String categoryKey) {
        Optional<TerritoryCategory> territoryCategory = territoryCategoryService.findByCategoryKey(categoryKey);
        if(territoryCategory.isEmpty()) return List.of();
        Specification<Territory> byCategoryId = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get(Territory_.TERRITORY_CATEGORY_ID),
                        territoryCategory.get().getTerritoryCategoryId()
                );
        return territoryRepository.findAll(byCategoryId);
    }

    private ResponseUserAddressTerritory compileResponseUserAddressTerritory(int territoryId) {
        Territory territory = findById(territoryId).orElse(null);
        if (territory == null) return null;

        ResponseGeoTerritory responseGeoTerritory = ResponseGeoTerritory
                .builder()
                .territoryId(territory.getTerritoryId())
                .territoryName(territory.getTerritoryName())
                .build();

        return ResponseUserAddressTerritory
                .builder()
                .orderId(0)
                .territory(responseGeoTerritory)
                .labelName(territory.getTerritoryCategory().getTerritoryCategoryKey())
                .labelId(territory.getTerritoryCategoryId())
                .build();
    }

    @Override
    public List<ResponseUserAddressTerritory> getBranchTerritories(int territoryId) {
        List<ResponseUserAddressTerritory> responseUserAddressTerritories = new ArrayList<>();
        XrefTerritorySubdivision xrefTerritorySubdivision = xrefTerritorySubdivisionService
                .findByTerritorySonId(territoryId).orElse(null);

        if (xrefTerritorySubdivision == null) return responseUserAddressTerritories;
        ResponseUserAddressTerritory responseUserAddressTerritory = compileResponseUserAddressTerritory(
                xrefTerritorySubdivision.getTerritorySonId()
        );
        if (responseUserAddressTerritory == null) return responseUserAddressTerritories;
        responseUserAddressTerritories.add(responseUserAddressTerritory);

        while (xrefTerritorySubdivision != null) {
            responseUserAddressTerritory = compileResponseUserAddressTerritory(
                    xrefTerritorySubdivision.getTerritoryMotherId()
            );
            if (responseUserAddressTerritory == null) break;
            responseUserAddressTerritories.add(responseUserAddressTerritory);
            xrefTerritorySubdivision = xrefTerritorySubdivisionService
                    .findByTerritorySonId(responseUserAddressTerritory.getTerritory().getTerritoryId())
                    .orElse(null);
        }

        responseUserAddressTerritories = responseUserAddressTerritories.reversed();
        for (int i = 0; i < responseUserAddressTerritories.size(); i++) {
            responseUserAddressTerritories.get(i).setOrderId(i + 1);
        }

        return responseUserAddressTerritories;
    }
}
