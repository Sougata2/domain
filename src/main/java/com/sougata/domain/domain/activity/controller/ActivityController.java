package com.sougata.domain.domain.activity.controller;

import com.sougata.domain.domain.activity.dto.ActivityDto;
import com.sougata.domain.domain.activity.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/activity")
public class ActivityController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ActivityService service;

    @GetMapping("/all")
    public ResponseEntity<List<ActivityDto>> findAll() {
        logger.info("findAll");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/by-sub-service/{id}")
    public ResponseEntity<List<ActivityDto>> findBySubServiceId(@PathVariable(value = "id") Long subServiceId) {
        logger.info("activity.findBySubServiceId : {}", subServiceId);
        return ResponseEntity.ok(service.findBySubServiceId(subServiceId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityDto> findById(@PathVariable Long id) {
        logger.info("findById : id = {}", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ActivityDto> create(@RequestBody ActivityDto dto) {
        logger.info("create : dto = {}", dto);
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping
    public ResponseEntity<ActivityDto> update(@RequestBody ActivityDto dto) {
        logger.info("update : dto = {}", dto);
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<ActivityDto> delete(@RequestBody ActivityDto dto) {
        logger.info("delete : dto = {}", dto);
        return ResponseEntity.ok(service.delete(dto));
    }
}
