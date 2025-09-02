package com.sougata.domain.domain.mandatoryDocument.repository;

import com.sougata.domain.domain.mandatoryDocument.entity.MandatoryDocumentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MandatoryDocumentsRepository extends JpaRepository<MandatoryDocumentsEntity, Long> {
    @Query("select e from MandatoryDocumentsEntity e where e.subService.id = :subServiceId")
    List<MandatoryDocumentsEntity> findBySubServiceId(Long subServiceId);
}
