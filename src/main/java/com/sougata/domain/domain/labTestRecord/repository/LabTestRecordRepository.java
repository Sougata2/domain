package com.sougata.domain.domain.labTestRecord.repository;

import com.sougata.domain.domain.labTestRecord.entity.LabTestRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LabTestRecordRepository extends JpaRepository<LabTestRecordEntity, Long> {
    @Query("select e from LabTestRecordEntity e where e.job.id = :jobId and e.template.id = :templateId")
    Optional<LabTestRecordEntity> findByJobIdAndTemplateId(Long jobId, Long templateId);
}
