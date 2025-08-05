package com.sougata.domain.domain.lab.controller;

import com.sougata.domain.domain.lab.dto.LabDto;
import com.sougata.domain.domain.lab.service.LabService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lab")
public class LabController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final LabService service;

    @GetMapping("/all")
    public ResponseEntity<List<LabDto>> findAll() {
        logger.info("lab.findAll");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabDto> findById(@PathVariable(value = "id") Long labId) {
        logger.info("lab.findById : {}", labId);
        return ResponseEntity.ok(service.findById(labId));
    }

    @PostMapping
    public ResponseEntity<LabDto> create(@RequestBody LabDto dto) {
        logger.info("lab.create : {}", dto);
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping
    public ResponseEntity<LabDto> update(@RequestBody LabDto dto) {
        logger.info("lab.update : {}", dto);
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<LabDto> delete(@RequestBody LabDto dto) {
        logger.info("lab.delete : {}", dto);
        return ResponseEntity.ok(service.delete(dto));
    }
}
