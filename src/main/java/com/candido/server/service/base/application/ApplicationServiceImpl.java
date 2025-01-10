package com.candido.server.service.base.application;

import com.candido.server.domain.v1.application.ApplicationRepository;
import com.candido.server.dto.v1.response.application.ResponseUserApplication;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ResponseUserApplication> findAllByAccountId(int accountId) {
        return applicationRepository.findAllByAccountId(accountId, ResponseUserApplication.class);
    }
}
