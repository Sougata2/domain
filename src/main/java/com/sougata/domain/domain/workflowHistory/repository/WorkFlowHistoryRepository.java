package com.sougata.domain.domain.workflowHistory.repository;

import com.sougata.domain.domain.workflowHistory.entity.WorkFlowHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkFlowHistoryRepository extends JpaRepository<WorkFlowHistoryEntity, Long> {
    @Query("select e from WorkFlowHistoryEntity e where e.application.referenceNumber = :referenceNumber order by e.createdAt asc")
    List<WorkFlowHistoryEntity> findByReferenceNumber(String referenceNumber);
}
