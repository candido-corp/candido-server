package com.candido.server.controller.opportunity;

import com.candido.server.dto.v1.response.opportunity.ResponseOpportunity;
import com.candido.server.dto.v1.request.util.RequestPagination;
import com.candido.server.dto.v1.response.util.ResponsePaginated;
import com.candido.server.service.business.opportunity.BusinessOpportunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/opportunities")
public class ControllerOpportunities {

    private final BusinessOpportunityService businessOpportunityService;

    @Autowired
    public ControllerOpportunities(BusinessOpportunityService businessOpportunityService) {
        this.businessOpportunityService = businessOpportunityService;
    }

    @GetMapping
    public ResponseEntity<ResponsePaginated<ResponseOpportunity>> getAllOpportunities(RequestPagination paginationRequest) {
        return ResponseEntity.ok(businessOpportunityService.getAllOpportunities(paginationRequest));
    }

}
