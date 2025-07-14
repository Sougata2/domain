package com.sougata.domain.domain.application.controller;

import com.sougata.domain.domain.application.dto.ApplicationDto;
import com.sougata.domain.domain.application.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/application")
public class ApplicationController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ApplicationService service;

    @GetMapping("/all")
    public ResponseEntity<List<ApplicationDto>> findAll() {
        logger.info("findAll");
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDto> findById(@PathVariable Long id) {
        logger.info("findById : id = {}", id);
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @GetMapping("/by-reference-id/{id}")
    public ResponseEntity<ApplicationDto> findByReferenceId(@PathVariable String id) {
        logger.info("ApplicationController findByReferenceId {}", id);
        try {
            return ResponseEntity.ok(service.findByReferenceNumber(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/by-status-and-user-id")
    public ResponseEntity<Page<ApplicationDto>> findByStatusAndUserId(
            @RequestParam(defaultValue = "AG") String status,
            @RequestParam(value = "user") Long userId,
            Pageable pageable
    ) {
        logger.info("findBy Status: {}, UserId: {} And Pageable: {}", userId, status, pageable);
        try {
            return ResponseEntity.ok(service.findByStatusNameAndUserId(status, userId, pageable));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity<ApplicationDto> create(@RequestBody ApplicationDto dto) {
        logger.info("create: dto = {}", dto);
        try {
            return ResponseEntity.ok(service.create(dto));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping
    public ResponseEntity<ApplicationDto> update(@RequestBody ApplicationDto dto) {
        logger.info("update: dto = {}", dto);
        try {
            return ResponseEntity.ok(service.update(dto));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping
    public ResponseEntity<ApplicationDto> delete(@RequestBody ApplicationDto dto) {
        logger.info("delete: dto = {}", dto);
        try {
            return ResponseEntity.ok(service.delete(dto));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
