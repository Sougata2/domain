package com.sougata.domain.domain.job.controller;

import com.sougata.domain.domain.job.dto.JobDto;
import com.sougata.domain.domain.job.dto.JobProcessDto;
import com.sougata.domain.domain.job.service.JobService;
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
@RequestMapping("/job")
public class JobController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JobWorkFlowHistoryService historyService;
    private final JobService service;

    @GetMapping("/by-device-id/{id}")
    public ResponseEntity<JobDto> findByDeviceId(@PathVariable(value = "id") Long deviceId) {
        logger.info("job.findByDeviceId : {}", deviceId);
        return ResponseEntity.ok(service.findByDeviceId(deviceId));
    }

    @GetMapping("/by-reference-number/{number}")
    public ResponseEntity<List<JobDto>> findByApplicationReferenceNumber(@PathVariable(value = "number") String referenceNumber) {
        logger.info("job.findByApplicationReferenceNumber : {}", referenceNumber);
        return ResponseEntity.ok(service.findByApplicationReferenceNumber(referenceNumber));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDto> findById(@PathVariable(value = "id") Long jobId) {
        logger.info("job.findById : {}", jobId);
        return ResponseEntity.ok(service.findById(jobId));
    }

    @PostMapping
    public ResponseEntity<JobDto> create(@RequestBody JobDto jobDto) {
        logger.info("job.create : {}", jobDto);
        return ResponseEntity.ok(service.create(jobDto));
    }

    @PostMapping("/do-next")
    public ResponseEntity<JobWorkFlowHistoryDto> doNext(@RequestBody JobProcessDto dto) {
        logger.info("job.doNext : {}", dto);
        JobWorkFlowHistoryDto processed = service.doNext(dto);
        return ResponseEntity.ok(historyService.create(processed));
    }

    @PutMapping
    public ResponseEntity<JobDto> update(@RequestBody JobDto jobDto) {
        logger.info("job.update : {}", jobDto);
        return ResponseEntity.ok(service.update(jobDto));
    }

    @DeleteMapping
    public ResponseEntity<JobDto> delete(@RequestBody JobDto jobDto) {
        logger.info("job.delete : {}", jobDto);
        return ResponseEntity.ok(service.delete(jobDto));
    }
}
