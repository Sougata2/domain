package com.sougata.domain.domain.devices.controller;

import com.sougata.domain.domain.devices.dto.DeviceDto;
import com.sougata.domain.domain.devices.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/device")
public class DeviceController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final DeviceService service;

    @GetMapping("/by-application-reference-number/{id}")
    public ResponseEntity<List<DeviceDto>> findByReferenceNumber(@PathVariable(name = "id") String referenceNumber) {
        logger.info("findByReferenceNumber referenceNumber : {}", referenceNumber);
        return ResponseEntity.ok(service.findByReferenceNumber(referenceNumber));
    }

    @GetMapping("/device-with-jobs/{id}")
    public ResponseEntity<List<Map<String, Object>>> findDeviceWithJobByApplicationReferenceNumber(@PathVariable(name = "id") String referenceNumber) {
        logger.info("findDeviceWithJobByApplicationReferenceNumber : {}", referenceNumber);
        return ResponseEntity.ok(service.findDeviceWithJobByApplicationReferenceNumber(referenceNumber));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDto> findById(@PathVariable(name = "id") Long deviceId) {
        logger.info("findById deviceId : {}", deviceId);
        return ResponseEntity.ok(service.findById(deviceId));
    }

    @PostMapping
    public ResponseEntity<DeviceDto> create(@RequestBody DeviceDto deviceDto) {
        logger.info("create deviceDto : {}", deviceDto);
        return ResponseEntity.ok(service.create(deviceDto));
    }

    @PutMapping
    public ResponseEntity<DeviceDto> update(@RequestBody DeviceDto deviceDto) {
        logger.info("update deviceDto : {}", deviceDto);
        return ResponseEntity.ok(service.update(deviceDto));
    }

    @DeleteMapping
    public ResponseEntity<DeviceDto> delete(@RequestBody DeviceDto deviceDto) {
        logger.info("delete deviceDto : {}", deviceDto);
        return ResponseEntity.ok(service.delete(deviceDto));
    }
}
