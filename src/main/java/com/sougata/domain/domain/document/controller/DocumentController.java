package com.sougata.domain.domain.document.controller;

import com.sougata.domain.domain.document.dto.DocumentDto;
import com.sougata.domain.domain.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/document")
public class DocumentController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final DocumentService service;

    @GetMapping("/by-reference-number/{id}")
    public ResponseEntity<List<DocumentDto>> findByReferenceNumber(@PathVariable(value = "id") String referenceNumber) {
        logger.info("document.findByReferenceNumber : {}", referenceNumber);
        return ResponseEntity.ok(service.findByReferenceNumber(referenceNumber));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentDto> findById(@PathVariable(value = "id") Long documentId) {
        logger.info("document.findById : {}", documentId);
        return ResponseEntity.ok(service.findById(documentId));
    }

    @PostMapping
    public ResponseEntity<DocumentDto> create(@RequestBody DocumentDto dto) {
        logger.info("document.create : {}", dto);
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping
    public ResponseEntity<DocumentDto> update(@RequestBody DocumentDto dto) {
        logger.info("document.update : {}", dto);
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<DocumentDto> delete(@RequestBody DocumentDto dto) {
        logger.info("document.delete : {}", dto);
        return ResponseEntity.ok(service.delete(dto));
    }

}
