package com.sougata.domain.domain.document.repository;

import com.sougata.domain.domain.document.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
    @Query("select e from DocumentEntity e where e.application.referenceNumber = :referenceNumber")
    List<DocumentEntity> findByReferenceNumber(String referenceNumber);
}
