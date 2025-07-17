package com.sougata.domain.domain.devices.repository;

import com.sougata.domain.domain.devices.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {
    @Query("select e from DeviceEntity e where e.application.referenceNumber = :referenceNumber")
    List<DeviceEntity> findByReferenceNumber(String referenceNumber);
}
