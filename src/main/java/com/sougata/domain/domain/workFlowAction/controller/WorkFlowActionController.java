package com.sougata.domain.domain.workFlowAction.controller;

import com.sougata.domain.domain.status.dto.StatusDto;
import com.sougata.domain.domain.workFlowAction.dto.WorkFlowActionDto;
import com.sougata.domain.domain.workFlowAction.service.WorkFlowActionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workflow-action")
public class WorkFlowActionController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final WorkFlowActionService service;

    @GetMapping("/by-status/{id}")
    public ResponseEntity<List<WorkFlowActionDto>> findByStatusId(@PathVariable(value = "id") Long statusId) {
        logger.info("workFlowAction.finByStatusId : {}", statusId);
        return ResponseEntity.ok(service.findByStatusId(statusId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkFlowActionDto> findById(@PathVariable(value = "id") Long actionId) {
        logger.info("workFlowAction.findById : {}", actionId);
        return ResponseEntity.ok(service.findById(actionId));
    }

    @GetMapping("/find-target-status/{id}")
    public ResponseEntity<List<StatusDto>> findTargetStatusByCurrentStatus(@PathVariable(value = "id") Long statusId) {
        logger.info("workFlowAction.findTargetStatusByCurrentStatus : {}", statusId);
        return ResponseEntity.ok(service.findTargetStatusByCurrentStatus(statusId));
    }

    @PostMapping
    public ResponseEntity<WorkFlowActionDto> create(@RequestBody WorkFlowActionDto dto) {
        logger.info("workFlowAction.create : {}", dto);
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping
    public ResponseEntity<WorkFlowActionDto> update(@RequestBody WorkFlowActionDto dto) {
        logger.info("workFlowAction.update : {}", dto);
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<WorkFlowActionDto> delete(@RequestBody WorkFlowActionDto dto) {
        logger.info("workFlowAction.delete : {}", dto);
        return ResponseEntity.ok(service.delete(dto));
    }
}
