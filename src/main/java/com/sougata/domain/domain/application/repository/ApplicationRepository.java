package com.sougata.domain.domain.application.repository;

import com.sougata.domain.domain.application.entity.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {
    @Query("select e from ApplicationEntity e where e.referenceNumber = :appId")
    Optional<ApplicationEntity> findByReferenceNumber(String appId);

    @Query("select e.id from ApplicationEntity e order by e.id desc limit 1")
    Optional<Long> findPrecedingId();
}
