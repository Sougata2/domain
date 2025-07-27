package com.sougata.domain.domain.formStages.controller;

import com.sougata.domain.domain.formStages.dto.FormStageDto;
import com.sougata.domain.domain.formStages.service.FormStageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/form-stage")
public class FormStageController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final FormStageService service;

    @GetMapping("/by-form-id/{id}")
    public ResponseEntity<List<FormStageDto>> findByFormId(@PathVariable(name = "id") Long formId) {
        logger.info("FormStageController:findByFormId:{}", formId);
        return ResponseEntity.ok(service.findByFormId(formId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormStageDto> findById(@PathVariable(name = "id") Long stageId) {
        logger.info("FormStageController:findById:{}", stageId);
        return ResponseEntity.ok(service.findById(stageId));
    }

    @PostMapping
    public ResponseEntity<FormStageDto> create(@RequestBody FormStageDto dto) {
        logger.info("FormStageController:save:{}", dto);
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping
    public ResponseEntity<FormStageDto> update(@RequestBody FormStageDto dto) {
        logger.info("FormStageController:update:{}", dto);
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<FormStageDto> delete(@RequestBody FormStageDto dto) {
        logger.info("FormStageController:delete:{}", dto);
        return ResponseEntity.ok(service.delete(dto));
    }
}
