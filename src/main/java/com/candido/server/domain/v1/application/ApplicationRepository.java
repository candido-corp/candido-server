package com.candido.server.domain.v1.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer>, JpaSpecificationExecutor<Application> {
    <T> List<T> findAllByAccountId(int accountId, Class<T> type);
}
