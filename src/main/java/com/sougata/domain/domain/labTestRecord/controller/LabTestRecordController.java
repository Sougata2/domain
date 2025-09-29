package com.sougata.domain.domain.labTestRecord.controller;

import com.sougata.domain.domain.labTestRecord.dto.LabTestRecordDto;
import com.sougata.domain.domain.labTestRecord.service.LabTestRecordService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lab-test-record")
public class LabTestRecordController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final LabTestRecordService service;

    @GetMapping("/by-template-and-job-id")
    public ResponseEntity<LabTestRecordDto> findByTemplateAndJobId(
            @RequestParam(value = "template") Long templateId,
            @RequestParam(value = "job") Long jobId
    ) {
        logger.info("LabTestRecordController.findByTemplateAndJobId : templateId = {}, jobId = {}", templateId, jobId);
        return ResponseEntity.ok(service.findByTemplateIdAndJobId(templateId, jobId));
    }

    @GetMapping("/get-record-count/{id}")
    public ResponseEntity<Map<Long, Object>> getTestRecordsCount(@PathVariable(value = "id") Long jobId) {
        logger.info("LabTestRecordController.getTestRecordsCount : jobId = {}", jobId);
        return ResponseEntity.ok(service.findTestRecordsCount(jobId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabTestRecordDto> findById(@PathVariable(value = "id") Long recordId) {
        logger.info("LabTestRecordController.findById : id = {}", recordId);
        return ResponseEntity.ok(service.findById(recordId));
    }

    @PostMapping
    public ResponseEntity<LabTestRecordDto> create(@RequestBody LabTestRecordDto dto) {
        logger.info("LabTestRecordController.create : {}", dto);
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping
    public ResponseEntity<LabTestRecordDto> update(@RequestBody LabTestRecordDto dto) {
        logger.info("LabTestRecordController.update : {}", dto);
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<LabTestRecordDto> delete(@RequestBody LabTestRecordDto dto) {
        logger.info("LabTestRecordController.delete : {}", dto);
        return ResponseEntity.ok(service.delete(dto));
    }
}
