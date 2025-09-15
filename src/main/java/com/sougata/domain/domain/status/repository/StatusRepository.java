package com.sougata.domain.domain.status.repository;

import com.sougata.domain.domain.status.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
    @Query("select e from StatusEntity e where e.name = :statusName")
    Optional<StatusEntity> findByStatusName(String statusName);
}