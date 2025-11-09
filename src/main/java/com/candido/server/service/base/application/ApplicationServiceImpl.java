package com.candido.server.service.base.application;

import com.candido.server.domain.v1.application.*;
import com.candido.server.domain.v1.opportunity.XrefAccountOpportunitySavedRepository;
import com.candido.server.dto.v1.response.opportunity.ResponseOpportunity;
import com.candido.server.dto.v1.response.application.ResponseUserApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final XrefAccountOpportunitySavedRepository xrefAccountOpportunitySavedRepository;

    @Autowired
    public ApplicationServiceImpl(
            ApplicationRepository applicationRepository,
            XrefAccountOpportunitySavedRepository xrefAccountOpportunitySavedRepository
    ) {
        this.applicationRepository = applicationRepository;
        this.xrefAccountOpportunitySavedRepository = xrefAccountOpportunitySavedRepository;
    }

    @Override
    public List<Application> getUserApplicationsByStatus(Long accountId, int statusId) {
        Specification<Application> byStatus = (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get(Application_.ACCOUNT_ID), accountId),
                criteriaBuilder.equal(root.get(Application_.APPLICATION_STATUS_ID), statusId)
        );
        return applicationRepository.findAll(byStatus);
    }

    @Override
    public List<ResponseUserApplication> findAllByAccountId(Long accountId, Integer statusId) {
        if(statusId != null) {
            return getUserApplicationsByStatus(accountId, statusId)
                    .stream().map(ResponseUserApplication::mapToResponseUserApplication).toList();
        }
        return applicationRepository.findAllByAccountId(accountId, ResponseUserApplication.class);
    }

    @Override
    public List<ResponseOpportunity> findAllApplicationsSavedByAccountId(Long accountId) {
        return xrefAccountOpportunitySavedRepository.findAllByIdAccountId(accountId)
                .stream()
                .map(xrefAccountApplicationSaved ->
                        ResponseOpportunity.mapToResponseOpportunity(
                                xrefAccountApplicationSaved.getOpportunity()
                        )
                ).toList();
    }

    @Override
    public boolean userHasOpenApplications(Long accountId) {
        return !getUserApplicationsByStatus(accountId, EnumApplicationStatus.IN_PROGRESS.getStatusId()).isEmpty();
    }
}
