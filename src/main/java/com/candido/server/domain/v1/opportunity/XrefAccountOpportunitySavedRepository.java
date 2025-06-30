package com.candido.server.domain.v1.opportunity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface XrefAccountOpportunitySavedRepository extends
        JpaRepository<XrefAccountOpportunitySaved, XrefAccountOpportunitySavedId>,
        JpaSpecificationExecutor<XrefAccountOpportunitySaved>
{
    List<XrefAccountOpportunitySaved> findAllByIdAccountId(Long accountId);
}
