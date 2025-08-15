package com.sougata.domain.domain.mandatoryDocument.controller;

import com.sougata.domain.domain.mandatoryDocument.dto.MandatoryDocumentsDto;
import com.sougata.domain.domain.mandatoryDocument.service.MandatoryDocumentsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mandatory-document")
public class MandatoryDocumentController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MandatoryDocumentsService service;

    @GetMapping("/all")
    public ResponseEntity<List<MandatoryDocumentsDto>> findAll() {
        logger.info("mandatoryDocument.findAll()");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/by-reference-number/{id}")
    public ResponseEntity<List<MandatoryDocumentsDto>> getByReferenceNumber(@PathVariable(value = "id") String referenceNumber) {
        logger.info("mandatoryDocument.getByReferenceNumber : {}", referenceNumber);
        return ResponseEntity.ok(service.findByReferenceNumber(referenceNumber));
    }

    @GetMapping("/by-form-id/{id}")
    public ResponseEntity<List<MandatoryDocumentsDto>> getByFormId(@PathVariable(value = "id") Long formId) {
        logger.info("mandatoryDocument.getByFormId : {}", formId);
        return ResponseEntity.ok(service.findByFormId(formId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MandatoryDocumentsDto> getById(@PathVariable(value = "id") Long id) {
        logger.info("mandatoryDocument.getById : {}", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<MandatoryDocumentsDto> create(@RequestBody MandatoryDocumentsDto dto) {
        logger.info("mandatoryDocument.create : {}", dto);
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping
    public ResponseEntity<MandatoryDocumentsDto> update(@RequestBody MandatoryDocumentsDto dto) {
        logger.info("mandatoryDocument.update : {}", dto);
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<MandatoryDocumentsDto> delete(@RequestBody MandatoryDocumentsDto dto) {
        logger.info("mandatoryDocument.delete : {}", dto);
        return ResponseEntity.ok(service.delete(dto));
    }
}
