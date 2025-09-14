package com.sougata.domain.domain.jobWorkFlowHistory.repository;

import com.sougata.domain.domain.jobWorkFlowHistory.entity.JobWorkFlowHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobWorkFlowHistoryRepository extends JpaRepository<JobWorkFlowHistoryEntity, Long> {
    @Query("select e from JobWorkFlowHistoryEntity e where e.job.id = :jobId order by e.createdAt asc")
    List<JobWorkFlowHistoryEntity> findByJobId(Long jobId);
}
