package com.candido.server.domain.v1.geo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TerritoryCategoryRepository extends JpaRepository<TerritoryCategory, Integer>, JpaSpecificationExecutor<TerritoryCategory> {
}
