package com.candido.server.service.base.application;

import com.candido.server.domain.v1.application.Application;
import com.candido.server.dto.v1.response.application.ResponseUserApplication;

import java.util.List;

public interface ApplicationService {
    List<Application> getUserApplicationsByStatus(int accountId, int statusId);
    List<ResponseUserApplication> findAllByAccountId(int accountId);
    boolean userHasOpenApplications(int accountId);
}
