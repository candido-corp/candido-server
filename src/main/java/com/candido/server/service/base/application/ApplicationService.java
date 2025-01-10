package com.candido.server.service.base.application;

import com.candido.server.dto.v1.response.application.ResponseUserApplication;

import java.util.List;

public interface ApplicationService {
    List<ResponseUserApplication> findAllByAccountId(int accountId);
}
