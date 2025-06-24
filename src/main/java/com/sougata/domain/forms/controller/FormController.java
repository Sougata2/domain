package com.sougata.domain.forms.controller;

import com.sougata.domain.forms.dto.FormDto;
import com.sougata.domain.forms.service.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/form")
public class FormController {
    private final FormService service;

    @GetMapping("/all")
    public ResponseEntity<List<FormDto>> findAllForms() {
        return ResponseEntity.ok(service.findAllForms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormDto> findFormById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findFormById(id));
    }

    @PostMapping
    public ResponseEntity<FormDto> createForm(@RequestBody FormDto formDto) {
        return ResponseEntity.ok(service.createForm(formDto));
    }

    @PutMapping
    public ResponseEntity<FormDto> updateForm(@RequestBody FormDto formDto) {
        return ResponseEntity.ok(service.updateForm(formDto));
    }

    @DeleteMapping
    public ResponseEntity<FormDto> deleteForm(@RequestBody FormDto formDto) {
        return ResponseEntity.ok(service.deleteForm(formDto));
    }
}
