package com.sougata.domain.domain.certifications.controller;

import com.sougata.domain.domain.certifications.dto.CertificationDto;
import com.sougata.domain.domain.certifications.service.CertificationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/certificate")
public class CertificationController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CertificationService service;

    @GetMapping("/by-reference-number/{number}")
    public ResponseEntity<List<CertificationDto>> findByReferenceNumber(@PathVariable(value = "number") String referenceNumber) {
        logger.info("CertificationController findByReferenceNumber : {}", referenceNumber);
        return ResponseEntity.ok(service.findByApplicationReferenceNumber(referenceNumber));
    }

    @GetMapping("/by-certificate-number/{number}")
    public ResponseEntity<CertificationDto> findByCertificateNumber(@PathVariable(value = "number") String certificateNumber) {
        logger.info("CertificationController.findByCertificateNumber : {}", certificateNumber);
        return ResponseEntity.ok(service.findByCertificateNumber(certificateNumber));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificationDto> findById(@PathVariable(value = "id") Long id) {
        logger.info("CertificationController.findById : {}", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<CertificationDto> create(@RequestBody CertificationDto dto) {
        logger.info("CertificationController.create : {}", dto);
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping
    public ResponseEntity<CertificationDto> update(@RequestBody CertificationDto dto) {
        logger.info("CertificationController.update : {}", dto);
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<CertificationDto> delete(@RequestBody CertificationDto dto) {
        logger.info("CertificationController.delete : {}", dto);
        return ResponseEntity.ok(service.delete(dto));
    }
}
