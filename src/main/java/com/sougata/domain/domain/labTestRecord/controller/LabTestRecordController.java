package com.sougata.domain.domain.labTestRecord.controller;

import com.sougata.domain.domain.labTestRecord.dto.LabTestRecordDto;
import com.sougata.domain.domain.labTestRecord.service.LabTestRecordService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lab-test-record")
public class LabTestRecordController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final LabTestRecordService service;

    @PostMapping
    public ResponseEntity<LabTestRecordDto> create(@RequestBody LabTestRecordDto dto) {
        logger.info("LabTestRecordController.create : {}", dto);
        return ResponseEntity.ok(service.create(dto));
    }
}
