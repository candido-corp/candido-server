package com.candido.server.domain.v1.opportunity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OpportunityRepository extends
        JpaRepository<Opportunity, Long>,
        JpaSpecificationExecutor<Opportunity>
{
}
