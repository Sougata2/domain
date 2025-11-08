package com.sougata.domain.domain.status.controller;

import com.sougata.domain.domain.status.dto.StatusDto;
import com.sougata.domain.domain.status.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/status")
public class StatusController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final StatusService service;

    @GetMapping("/all")
    public ResponseEntity<List<StatusDto>> findAll() {
        logger.debug("findAll");
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/by-status-name/{name}")
    public ResponseEntity<StatusDto> findByStatusName(@PathVariable String name) {
        logger.debug("findByStatusName: {}", name);
        return ResponseEntity.ok(service.findByStatusName(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusDto> findById(@PathVariable Long id) {
        logger.debug("find by id {}", id);
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity<StatusDto> create(@RequestBody StatusDto dto) {
        logger.debug("create: {}", dto);
        try {
            return ResponseEntity.ok(service.create(dto));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping
    public ResponseEntity<StatusDto> update(@RequestBody StatusDto dto) {
        logger.debug("update: {}", dto);
        try {
            return ResponseEntity.ok(service.update(dto));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping
    public ResponseEntity<StatusDto> delete(@RequestBody StatusDto dto) {
        logger.debug("delete: {}", dto);
        try {
            return ResponseEntity.ok(service.delete(dto));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
