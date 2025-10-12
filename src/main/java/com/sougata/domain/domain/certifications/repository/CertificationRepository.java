package com.sougata.domain.domain.certifications.repository;

import com.sougata.domain.domain.certifications.entity.CertificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificationRepository extends JpaRepository<CertificationEntity, Long> {
    @Query("select e from CertificationEntity e where e.application.referenceNumber = :referenceNumber")
    List<CertificationEntity> findByApplicationReferenceNumber(String referenceNumber);

    @Query("select e from CertificationEntity e where e.certificateNumber = :certificateNumber")
    Optional<CertificationEntity> findByCertificateNumber(String certificateNumber);
}
