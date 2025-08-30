package com.sougata.domain.domain.workFlowGroup.repository;

import com.sougata.domain.domain.workFlowGroup.entity.WorkFlowGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkFlowGroupRepository extends JpaRepository<WorkFlowGroupEntity, Long>, JpaSpecificationExecutor<WorkFlowGroupEntity> {
}
