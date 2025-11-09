package com.candido.server.service.base.geo;

import com.candido.server.domain.v1.geo.TerritoryCategory;
import com.candido.server.domain.v1.geo.TerritoryCategoryRepository;
import com.candido.server.domain.v1.geo.TerritoryCategory_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TerritoryCategoryServiceImpl implements TerritoryCategoryService {

    private final TerritoryCategoryRepository territoryCategoryRepository;

    @Autowired
    TerritoryCategoryServiceImpl(TerritoryCategoryRepository territoryCategoryRepository) {
        this.territoryCategoryRepository = territoryCategoryRepository;
    }

    @Override
    public Optional<TerritoryCategory> findByCategoryKey(String categoryKey) {
        Specification<TerritoryCategory> byCategoryKey = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(TerritoryCategory_.TERRITORY_CATEGORY_KEY), categoryKey);

        return territoryCategoryRepository.findOne(byCategoryKey);
    }
}
