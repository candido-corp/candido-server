package com.candido.server.service.business.opportunity;

import com.candido.server.domain.v1.opportunity.Opportunity;
import com.candido.server.dto.v1.response.opportunity.ResponseOpportunity;
import com.candido.server.dto.v1.request.util.RequestPagination;
import com.candido.server.dto.v1.response.util.ResponsePaginated;
import com.candido.server.service.base._common.pagination.PageRequestBuilder;
import com.candido.server.service.base.opportunity.OpportunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BusinessOpportunityServiceImpl implements BusinessOpportunityService {

    private final OpportunityService opportunityService;

    @Autowired
    public BusinessOpportunityServiceImpl(OpportunityService opportunityService) {
        this.opportunityService = opportunityService;
    }

    @Override
    public ResponsePaginated<ResponseOpportunity> getAllOpportunities(RequestPagination paginationRequest) {
        Pageable pageable = PageRequestBuilder.from(paginationRequest);
        Page<Opportunity> page = opportunityService.findAll(pageable);
        return ResponsePaginated.fromPage(page.map(ResponseOpportunity::mapToResponseOpportunity));
    }
}
