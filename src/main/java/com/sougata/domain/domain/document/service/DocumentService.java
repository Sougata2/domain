package com.sougata.domain.domain.document.service;

import com.sougata.domain.domain.document.dto.DocumentDto;

import java.util.List;


public interface DocumentService {
    List<DocumentDto> findByReferenceNumber(String referenceNumber);

    DocumentDto findById(Long id);

    DocumentDto create(DocumentDto dto);

    DocumentDto update(DocumentDto dto);

    DocumentDto delete(DocumentDto dto);
}
