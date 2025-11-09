package com.candido.server.service.base.opportunity;

import com.candido.server.domain.v1.opportunity.Opportunity;
import com.candido.server.domain.v1.opportunity.OpportunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OpportunityServiceImpl implements OpportunityService {

    private final OpportunityRepository opportunityRepository;

    @Autowired
    public OpportunityServiceImpl(OpportunityRepository opportunityRepository) {
        this.opportunityRepository = opportunityRepository;
    }

    @Override
    public Page<Opportunity> findAll(Pageable pageable) {
        return opportunityRepository.findAll(pageable);
    }
}

