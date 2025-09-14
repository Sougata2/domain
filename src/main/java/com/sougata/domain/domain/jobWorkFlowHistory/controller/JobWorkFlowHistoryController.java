package com.sougata.domain.domain.jobWorkFlowHistory.controller;

import com.sougata.domain.domain.jobWorkFlowHistory.dto.JobWorkFlowHistoryDto;
import com.sougata.domain.domain.jobWorkFlowHistory.service.JobWorkFlowHistoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/job-workflow-history")
public class JobWorkFlowHistoryController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JobWorkFlowHistoryService service;

    @GetMapping("/by-job-id/{id}")
    public ResponseEntity<List<JobWorkFlowHistoryDto>> findByJobId(@PathVariable(value = "id") Long jobId) {
        logger.info("jobWorkFlowHistory.findByJobId {}", jobId);
        return ResponseEntity.ok(service.findByJobId(jobId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobWorkFlowHistoryDto> findById(@PathVariable(value = "id") Long id) {
        logger.info("jobWorkFlowHistory.findById {}", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<JobWorkFlowHistoryDto> create(@RequestBody JobWorkFlowHistoryDto dto) {
        logger.info("jobWorkFlowHistory.create {}", dto);
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping
    public ResponseEntity<JobWorkFlowHistoryDto> update(@RequestBody JobWorkFlowHistoryDto dto) {
        logger.info("jobWorkFlowHistory.update {}", dto);
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<JobWorkFlowHistoryDto> delete(@RequestBody JobWorkFlowHistoryDto dto) {
        logger.info("jobWorkFlowHistory.delete {}", dto);
        return ResponseEntity.ok(service.delete(dto));
    }
}
