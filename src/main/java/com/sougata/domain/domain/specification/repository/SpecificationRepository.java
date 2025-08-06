package com.sougata.domain.domain.specification.repository;

import com.sougata.domain.domain.specification.entity.SpecificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SpecificationRepository extends JpaRepository<SpecificationEntity, Long> {
    @Query("select e from SpecificationEntity e " +
            "join fetch e.activities a " +
            "where a.id in :activityIds")
    List<SpecificationEntity> findByActivityIds(Set<Long> activityIds);
}