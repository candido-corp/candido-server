package com.candido.server.service.base.application;

import com.candido.server.domain.v1.application.Application;
import com.candido.server.domain.v1.application.ApplicationRepository;
import com.candido.server.domain.v1.application.Application_;
import com.candido.server.domain.v1.application.EnumApplicationStatus;
import com.candido.server.dto.v1.response.application.ResponseUserApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationServiceImpl(
            ApplicationRepository applicationRepository
    ) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public List<Application> getUserApplicationsByStatus(int accountId, int statusId) {
        Specification<Application> byStatus = (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get(Application_.ACCOUNT_ID), accountId),
                criteriaBuilder.equal(root.get(Application_.APPLICATION_STATUS_ID), statusId)
        );
        return applicationRepository.findAll(byStatus);
    }

    @Override
    public List<ResponseUserApplication> findAllByAccountId(int accountId, Integer statusId) {
        if(statusId != null) {
            return getUserApplicationsByStatus(accountId, statusId)
                    .stream().map(ResponseUserApplication::mapToResponseUserApplication).toList();
        }
        return applicationRepository.findAllByAccountId(accountId, ResponseUserApplication.class);
    }

    @Override
    public boolean userHasOpenApplications(int accountId) {
        return !getUserApplicationsByStatus(accountId, EnumApplicationStatus.IN_PROGRESS.getStatusId()).isEmpty();
    }
}
