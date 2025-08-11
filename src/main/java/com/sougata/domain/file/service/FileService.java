package com.sougata.domain.file.service;

import com.sougata.domain.file.dto.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileService {
    FileDto upload(MultipartFile file);

    File download(Long id);
}
