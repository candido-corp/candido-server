package com.candido.server.domain.v1.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface XrefAccountApplicationSavedRepository extends
        JpaRepository<XrefAccountApplicationSaved, XrefAccountApplicationSavedIdentity>,
        JpaSpecificationExecutor<XrefAccountApplicationSaved>
{
    List<XrefAccountApplicationSaved> findAllByAccountId(int accountId);
}
