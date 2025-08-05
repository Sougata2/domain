package com.sougata.domain.domain.lab.repository;

import com.sougata.domain.domain.lab.entity.LabEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabRepository extends JpaRepository<LabEntity, Long> {
}
