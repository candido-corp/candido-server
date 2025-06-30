package com.candido.server.service.business.opportunity;

import com.candido.server.dto.v1.response.opportunity.ResponseOpportunity;
import com.candido.server.dto.v1.request.util.RequestPagination;
import com.candido.server.dto.v1.response.util.ResponsePaginated;

public interface BusinessOpportunityService {
    ResponsePaginated<ResponseOpportunity> getAllOpportunities(RequestPagination paginationRequest);
}
