package com.candido.server.domain.v1.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountSettingsRepository extends
        JpaRepository<AccountSettings, Integer>,
        JpaSpecificationExecutor<AccountSettings>
{}
