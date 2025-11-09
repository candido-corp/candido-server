package com.candido.server.service.base.opportunity;

import com.candido.server.domain.v1.opportunity.Opportunity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OpportunityService {
    Page<Opportunity> findAll(Pageable pageable);
}