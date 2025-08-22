package com.sougata.domain.domain.workflowHistory.controller;

import com.sougata.domain.domain.workflowHistory.dto.WorkFlowHistoryDto;
import com.sougata.domain.domain.workflowHistory.service.WorkFlowHistoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workflow-history")
public class WorkFlowHistoryController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final WorkFlowHistoryService service;

    @GetMapping("/by-reference-number/{id}")
    public ResponseEntity<List<WorkFlowHistoryDto>> findByReferenceNumber(@PathVariable(name = "id") String referenceNumber) {
        logger.info("workFlowHistory.findByReferenceNumber : {}", referenceNumber);
        return ResponseEntity.ok(service.findByReferenceNumber(referenceNumber));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkFlowHistoryDto> findById(@PathVariable(name = "id") Long workflowHistoryId) {
        logger.info("workFlowHistory.findById : {}", workflowHistoryId);
        return ResponseEntity.ok(service.findById(workflowHistoryId));
    }

    @PostMapping
    public ResponseEntity<WorkFlowHistoryDto> create(@RequestBody WorkFlowHistoryDto dto) {
        logger.info("workFlowHistory.add : {}", dto);
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping
    public ResponseEntity<WorkFlowHistoryDto> update(@RequestBody WorkFlowHistoryDto dto) {
        logger.info("workFlowHistory.update : {}", dto);
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<WorkFlowHistoryDto> delete(@RequestBody WorkFlowHistoryDto dto) {
        logger.info("workFlowHistory.delete : {}", dto);
        return ResponseEntity.ok(service.delete(dto));
    }
}
