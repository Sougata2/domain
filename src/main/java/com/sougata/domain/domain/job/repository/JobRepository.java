package com.sougata.domain.domain.job.repository;

import com.sougata.domain.domain.job.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, Long> {
    @Query("select e from JobEntity e where e.device.id = :deviceId")
    Optional<JobEntity> findJobByDeviceId(Long deviceId);
}