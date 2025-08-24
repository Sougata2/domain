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
            "where e.id != :statusId and e.id not in ( " +
            "select f.targetStatus.id from WorkFlowActionEntity f " +
            "where f.status.id = :statusId " +
            ") " +
            "order by e.id")
    List<StatusEntity> findTargetStatusByCurrentStatus(Long statusId);
}
