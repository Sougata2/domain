package com.sougata.domain.domain.certifications.service;

import com.sougata.domain.domain.certifications.dto.CertificationDto;

import java.util.List;

public interface CertificationService {
    List<CertificationDto> findByApplicationReferenceNumber(String referenceNumber);

    CertificationDto findById(Long id);

    CertificationDto findByCertificateNumber(String certificateNumber);

    CertificationDto create(CertificationDto dto);

    CertificationDto update(CertificationDto dto);

    CertificationDto delete(CertificationDto dto);
}
