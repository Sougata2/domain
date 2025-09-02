package com.sougata.domain.domain.mandatoryDocument.service;

import com.sougata.domain.domain.mandatoryDocument.dto.MandatoryDocumentsDto;

import java.util.List;

public interface MandatoryDocumentsService {
    List<MandatoryDocumentsDto> findAll();

    List<MandatoryDocumentsDto> findByReferenceNumber(String referenceNumber);

    List<MandatoryDocumentsDto> findBySubServiceId(Long subServiceId);

    MandatoryDocumentsDto findById(Long id);

    MandatoryDocumentsDto create(MandatoryDocumentsDto dto);

    MandatoryDocumentsDto update(MandatoryDocumentsDto dto);

    MandatoryDocumentsDto delete(MandatoryDocumentsDto dto);
}
