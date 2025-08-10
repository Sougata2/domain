package com.sougata.domain.file.controller;

import com.sougata.domain.file.dto.FileDto;
import com.sougata.domain.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final FileService service;

    @PostMapping("/upload")
    public ResponseEntity<FileDto> upload(@RequestParam(value = "file") MultipartFile file) {
        logger.info("file.upload {}", file.getOriginalFilename());
        return ResponseEntity.ok(service.upload(file));
    }
}
