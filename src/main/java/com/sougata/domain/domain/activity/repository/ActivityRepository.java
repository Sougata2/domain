package com.sougata.domain.domain.activity.repository;

import com.sougata.domain.domain.activity.entity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityEntity, Long> {
    @Query("select e from ActivityEntity e " +
            "join fetch e.subServices se " +
            "where se.id = :subServiceId " +
            "order by e.name")
    List<ActivityEntity> findBySubServiceId(Long subServiceId);
}
