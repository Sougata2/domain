package com.sougata.domain.domain.workFlowGroup.controller;

import com.sougata.domain.domain.workFlowGroup.dto.WorkFlowGroupDto;
import com.sougata.domain.domain.workFlowGroup.service.WorkFlowGroupService;
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
@RequestMapping("/workflow-group")
public class WorkFlowGroupController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final WorkFlowGroupService service;

    @GetMapping("/all")
    public ResponseEntity<List<WorkFlowGroupDto>> findAll() {
        logger.info("workFlowGroup.findAll");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkFlowGroupDto> findById(@PathVariable(value = "id") Long groupId) {
        logger.info("workFlowGroup.findById: {}", groupId);
        return ResponseEntity.ok(service.findById(groupId));
    }

    @PostMapping("/search")
    public ResponseEntity<Page<WorkFlowGroupDto>> search(@RequestBody Map<String, String> filter, Pageable pageable) {
        logger.info("workFlowGroup.search: {}, {}", filter, pageable);
        return ResponseEntity.ok(service.search(filter, pageable));
    }

    @PostMapping
    public ResponseEntity<WorkFlowGroupDto> create(@RequestBody WorkFlowGroupDto dto) {
        logger.info("workFlowGroup.create: {}", dto);
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping
    public ResponseEntity<WorkFlowGroupDto> update(@RequestBody WorkFlowGroupDto dto) {
        logger.info("workFlowGroup.update: {}", dto);
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<WorkFlowGroupDto> delete(@RequestBody WorkFlowGroupDto dto) {
        logger.info("workFlowGroup.delete: {}", dto);
        return ResponseEntity.ok(service.delete(dto));
    }
}
