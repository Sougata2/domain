package com.sougata.domain.domain.workFlowAction.repository;

import com.sougata.domain.domain.workFlowAction.entity.WorkFlowActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkFlowActionRepository extends JpaRepository<WorkFlowActionEntity, Long>, JpaSpecificationExecutor<WorkFlowActionEntity> {
    @Query("select e from WorkFlowActionEntity e where e.status.id = :id")
    List<WorkFlowActionEntity> findByStatusId(Long statusId);
}
