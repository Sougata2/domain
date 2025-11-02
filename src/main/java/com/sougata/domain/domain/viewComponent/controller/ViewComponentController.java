package com.sougata.domain.domain.viewComponent.controller;

import com.sougata.domain.domain.viewComponent.dto.ViewComponentDto;
import com.sougata.domain.domain.viewComponent.service.ViewComponentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/view-component")
public class ViewComponentController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ViewComponentService service;

    @GetMapping("/all")
    public ResponseEntity<List<ViewComponentDto>> findAll() {
        logger.info("ViewComponentController.findAll");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/by-role-and-application-type")
    public ResponseEntity<List<ViewComponentDto>> findByRoleIdAndApplicationType(@RequestParam(value = "role") Long roleId, @RequestParam(value = "type") String applicationType) {
        logger.info("ViewComponentController.findByRoleIdAndApplicationType : roleId={}, applicationType={}", roleId, applicationType);
        return ResponseEntity.ok(service.findAllByRoleIdAndApplicationType(roleId, applicationType));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViewComponentDto> findById(@PathVariable Long id) {
        logger.info("ViewComponentController.findById : id = {}", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ViewComponentDto> create(@RequestBody ViewComponentDto dto) {
        logger.info("ViewComponentController.create : dto={}", dto);
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping
    public ResponseEntity<ViewComponentDto> update(@RequestBody ViewComponentDto dto) {
        logger.info("ViewComponentController.update : dto={}", dto);
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<ViewComponentDto> delete(@RequestBody ViewComponentDto dto) {
        logger.info("ViewComponentController.delete : dto={}", dto);
        return ResponseEntity.ok(service.delete(dto));
    }
}
