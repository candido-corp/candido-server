package com.candido.server.service.base.application;

import com.candido.server.domain.v1.application.ApplicationStatus;

import java.util.List;
import java.util.Optional;

public interface ApplicationStatusService {
    Optional<ApplicationStatus> findById(int applicationStatusId);
    List<ApplicationStatus> findAll();
}
