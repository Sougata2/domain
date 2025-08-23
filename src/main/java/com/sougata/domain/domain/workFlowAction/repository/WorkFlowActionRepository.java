package com.sougata.domain.domain.workFlowAction.repository;

import com.sougata.domain.domain.status.entity.StatusEntity;
import com.sougata.domain.domain.workFlowAction.entity.WorkFlowActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkFlowActionRepository extends JpaRepository<WorkFlowActionEntity, Long>, JpaSpecificationExecutor<WorkFlowActionEntity> {
    @Query("select e from WorkFlowActionEntity e where e.status.id = :id")
    List<WorkFlowActionEntity> findByStatusId(Long statusId);


    @Query("select e from StatusEntity e " +
            "left join WorkFlowActionEntity f " +
            "on f.targetStatus.id = e.id " +
            "where f.targetStatus.id is null and e.id  != :statusId")
    List<StatusEntity> findTargetStatusByCurrentStatus(Long statusId);
}
