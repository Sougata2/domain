package com.sougata.domain.domain.mandatoryDocument.service;

import com.sougata.domain.domain.mandatoryDocument.dto.MandatoryDocumentsDto;

import java.util.List;

public interface MandatoryDocumentsService {
    List<MandatoryDocumentsDto> findAll();

    List<MandatoryDocumentsDto> findByFormId(Long formId);

    MandatoryDocumentsDto findById(Long id);

    MandatoryDocumentsDto create(MandatoryDocumentsDto dto);

    MandatoryDocumentsDto update(MandatoryDocumentsDto dto);

    MandatoryDocumentsDto delete(MandatoryDocumentsDto dto);
}
