package com.sougata.domain.domain.subService.controller;

import com.sougata.domain.domain.subService.dto.SubServiceDto;
import com.sougata.domain.domain.subService.service.SubServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sub-service")
public class SubServiceController {
    private final SubServiceService service;

    @GetMapping("/all")
    public ResponseEntity<List<SubServiceDto>> findAllSubService() {
        return ResponseEntity.ok(service.findAllSubServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubServiceDto> findSubServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findSubServiceById(id));
    }

    @PostMapping
    public ResponseEntity<SubServiceDto> createSubService(@RequestBody SubServiceDto dto) {
        return ResponseEntity.ok(service.createSubService(dto));
    }

    @PutMapping
    public ResponseEntity<SubServiceDto> updateSubService(@RequestBody SubServiceDto dto) {
        return ResponseEntity.ok(service.updateSubService(dto));
    }

    @DeleteMapping
    public ResponseEntity<SubServiceDto> deleteSubService(@RequestBody SubServiceDto dto) {
        return ResponseEntity.ok(service.deleteSubService(dto));
    }
}
