package com.sougata.domain.domain.specification.controller;

import com.sougata.domain.domain.specification.dto.SpecificationDto;
import com.sougata.domain.domain.specification.service.SpecificationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/specification")
public class SpecificationController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SpecificationService service;

    @GetMapping("/all")
    public ResponseEntity<List<SpecificationDto>> findAll() {
        logger.info("findAll");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/by-activity-ids")
    public ResponseEntity<List<SpecificationDto>> findByActivityId(@RequestParam(value = "ids") Set<Long> activityIds) {
        logger.debug("findByActivityId id: {}", activityIds);
        return ResponseEntity.ok(service.findByActivityIds(activityIds));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecificationDto> findById(@PathVariable(name = "id") Long specificationId) {
        logger.debug("findById specificationId: {}", specificationId);
        return ResponseEntity.ok(service.findById(specificationId));
    }

    @PostMapping
    public ResponseEntity<SpecificationDto> create(@RequestBody SpecificationDto dto) {
        logger.debug("create dto: {}", dto);
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping
    public ResponseEntity<SpecificationDto> update(@RequestBody SpecificationDto dto) {
        logger.debug("update dto: {}", dto);
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<SpecificationDto> delete(@RequestBody SpecificationDto dto) {
        logger.debug("delete dto: {}", dto);
        return ResponseEntity.ok(service.delete(dto));
    }
}
