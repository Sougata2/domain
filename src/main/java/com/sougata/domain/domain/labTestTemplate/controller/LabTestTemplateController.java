package com.sougata.domain.domain.labTestTemplate.controller;

import com.sougata.domain.domain.labTestTemplate.dto.LabTestTemplateDto;
import com.sougata.domain.domain.labTestTemplate.service.LabTestTemplateService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lab-test-template")
public class LabTestTemplateController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final LabTestTemplateService service;

    @GetMapping("/by-job-id/{id}")
    public ResponseEntity<List<LabTestTemplateDto>> findByJobId(@PathVariable(value = "id") Long jobId) {
        logger.info("labTestTemplateController.findByJobId : {}", jobId);
        return ResponseEntity.ok(service.findByJobId(jobId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<LabTestTemplateDto>> findAll() {
        logger.info("labTestTemplateController.findAll");
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/search")
    public ResponseEntity<Page<LabTestTemplateDto>> search(@RequestBody Map<String, Object> filters, Pageable pageable) {
        logger.info("labTestTemplateController.search : {} & {}", filters, pageable);
        return ResponseEntity.ok(service.search(filters, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabTestTemplateDto> findById(@PathVariable(value = "id") Long id) {
        logger.info("labTestTemplateController.findById : {}", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<LabTestTemplateDto> create(@RequestBody LabTestTemplateDto dto) {
        logger.info("labTestTemplateController.create : {}", dto);
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping
    public ResponseEntity<LabTestTemplateDto> update(@RequestBody LabTestTemplateDto dto) {
        logger.info("labTestTemplateController.update : {}", dto);
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<LabTestTemplateDto> delete(@RequestBody LabTestTemplateDto dto) {
        logger.info("labTestTemplateController.delete : {}", dto);
        return ResponseEntity.ok(service.delete(dto));
    }
}
