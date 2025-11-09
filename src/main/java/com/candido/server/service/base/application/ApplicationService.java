package com.candido.server.service.base.application;

import com.candido.server.domain.v1.application.Application;
import com.candido.server.dto.v1.response.opportunity.ResponseOpportunity;
import com.candido.server.dto.v1.response.application.ResponseUserApplication;

import java.util.List;

public interface ApplicationService {
    List<Application> getUserApplicationsByStatus(Long accountId, int statusId);
    List<ResponseUserApplication> findAllByAccountId(Long accountId, Integer statusId);
    List<ResponseOpportunity> findAllApplicationsSavedByAccountId(Long accountId);
    boolean userHasOpenApplications(Long accountId);
}
