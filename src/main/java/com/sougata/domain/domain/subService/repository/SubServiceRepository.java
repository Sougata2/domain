package com.sougata.domain.domain.subService.repository;

import com.sougata.domain.domain.subService.entity.SubServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubServiceRepository extends JpaRepository<SubServiceEntity, Long> {
    @Query("select sse from SubServiceEntity sse " +
            "join fetch sse.services se " +
            "where se.id = :serviceId " +
            "order by sse.name")
    List<SubServiceEntity> findByServiceId(Long serviceId);
}
