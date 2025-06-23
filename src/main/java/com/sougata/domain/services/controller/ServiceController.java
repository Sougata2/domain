package com.sougata.domain.services.controller;

import com.sougata.domain.services.dto.ServiceDto;
import com.sougata.domain.services.service.ServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/service")
public class ServiceController {
    private final ServicesService service;

    @GetMapping("/all")
    public ResponseEntity<List<ServiceDto>> findAllServices(){
        return ResponseEntity.ok(service.findAllServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceDto> findServiceById(@PathVariable Long id){
        return ResponseEntity.ok(service.findServiceById(id));
    }

    @PostMapping
    public ResponseEntity<ServiceDto> createService(@RequestBody ServiceDto dto){
        return ResponseEntity.ok(service.createService(dto));
    }

    @PutMapping
    public ResponseEntity<ServiceDto> updateService(@RequestBody ServiceDto dto){
        return ResponseEntity.ok(service.updateService(dto));
    }

    @DeleteMapping
    public ResponseEntity<ServiceDto> deleteService(@RequestBody ServiceDto dto){
        return ResponseEntity.ok(service.deleteService(dto));
    }
}
