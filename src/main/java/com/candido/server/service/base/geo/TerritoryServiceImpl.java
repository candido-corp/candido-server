package com.candido.server.service.base.geo;

import com.candido.server.domain.v1.geo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TerritoryServiceImpl implements TerritoryService {

    private final TerritoryRepository territoryRepository;
    private final TerritoryCategoryService territoryCategoryService;

    @Autowired
    TerritoryServiceImpl(
            TerritoryRepository territoryRepository,
            TerritoryCategoryService territoryCategoryService
    ) {
        this.territoryRepository = territoryRepository;
        this.territoryCategoryService = territoryCategoryService;
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
}
