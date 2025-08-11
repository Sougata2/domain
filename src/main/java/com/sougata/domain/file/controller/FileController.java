package com.sougata.domain.file.controller;

import com.sougata.domain.file.dto.FileDto;
import com.sougata.domain.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

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

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable(value = "id") Long id) {
        logger.info("file.download {}", id);
        File file = service.download(id);
        Resource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.length())
                .body(resource);
    }
}
