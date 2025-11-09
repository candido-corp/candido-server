package com.candido.server.service.base.application;

import com.candido.server.domain.v1.application.ApplicationStatus;
import com.candido.server.domain.v1.application.ApplicationStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationStatusServiceImpl implements ApplicationStatusService {

    private final ApplicationStatusRepository applicationStatusRepository;

    @Autowired
    ApplicationStatusServiceImpl(
            ApplicationStatusRepository applicationStatusRepository
    ) {
        this.applicationStatusRepository = applicationStatusRepository;
    }

    @Override
    public Optional<ApplicationStatus> findById(int applicationStatusId) {
        return applicationStatusRepository.findById(applicationStatusId);
    }

    @Override
    public List<ApplicationStatus> findAll() {
        return applicationStatusRepository.findAll();
    }
}
