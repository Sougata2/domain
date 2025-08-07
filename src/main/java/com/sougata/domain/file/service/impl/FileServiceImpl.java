package com.sougata.domain.file.service.impl;

import com.sougata.domain.file.dto.FileDto;
import com.sougata.domain.file.properties.FileProperties;
import com.sougata.domain.file.repository.FileRepository;
import com.sougata.domain.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileProperties properties;
    private final FileRepository repository;
    private final Path root = Paths.get(properties.getLocation());

    @Override
    public FileDto upload(MultipartFile file) {
        return null;
    }

    @Override
    public File download(String checksum) {
        return null;
    }
}
