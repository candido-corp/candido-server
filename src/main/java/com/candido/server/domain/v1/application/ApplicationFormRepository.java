package com.candido.server.domain.v1.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationFormRepository extends JpaRepository<ApplicationForm, Integer>, JpaSpecificationExecutor<ApplicationForm> {
}
