package com.sougata.domain.file.service.impl;

import com.sougata.domain.exceptions.MimeTypeNotAllowedException;
import com.sougata.domain.file.dto.FileDto;
import com.sougata.domain.file.entity.FileEntity;
import com.sougata.domain.file.properties.FileProperties;
import com.sougata.domain.file.repository.FileRepository;
import com.sougata.domain.file.service.FileService;
import com.sougata.domain.mapper.RelationalMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileProperties properties;
    private final FileRepository repository;
    private final RelationalMapper mapper;

    @Override
    public FileDto upload(MultipartFile file) {
        try {
            Path root = Path.of(properties.getLocation());
            // create the target directory if not exists.
            Path targetDirectory = root
                    .resolve(String.valueOf(LocalDateTime.now().getYear()))
                    .resolve(LocalDateTime.now().getMonth().toString());

            if (Files.notExists(targetDirectory)) {
                Files.createDirectories(targetDirectory);
            }

            // calculate the file checksum
            String checksum;
            try (InputStream inputStream = file.getInputStream()) {
                checksum = DigestUtils.sha256Hex(inputStream);
            }

            // get file extension
            String fileExtension;
            if (file.getOriginalFilename() != null) {
                fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                Set<String> allowedExtensions = properties.getAllowedExtensions();
                if (!allowedExtensions.contains(fileExtension.substring(1))) {
                    throw new MimeTypeNotAllowedException("Only %s mimeTypes are allowed, your file's mimeType is %s".formatted(allowedExtensions.toString(), fileExtension));
                }
            } else {
                throw new RuntimeException("No file name");
            }

            // resolve duplicate file upload.
            Path targetFile = targetDirectory.resolve(Path.of(checksum + fileExtension));
            int duplicateCount = 1;
            while (Files.exists(targetFile)) {
                String newFileName = "%s(%d)".formatted(checksum, duplicateCount++);
                targetFile = targetDirectory.resolve(Path.of(newFileName + fileExtension));
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, targetFile);
            }

            FileDto fileDto = new FileDto();
            fileDto.setName(targetFile.getFileName().toString());
            fileDto.setSize(file.getSize());
            fileDto.setExtension(fileExtension);
            fileDto.setLocation(targetFile.toString());

            return create(fileDto);
        } catch (MimeTypeNotAllowedException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File download(Long id) {
        try {
            Optional<FileEntity> entity = repository.findById(id);
            if (entity.isEmpty()) {
                throw new FileNotFoundException("FileEntity with %d is not found".formatted(id));
            }
            Path filePath = Path.of(entity.get().getLocation());
            return filePath.toFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private FileDto create(FileDto dto) {
        try {
            FileEntity entity = (FileEntity) mapper.mapToEntity(dto);
            FileEntity saved = repository.save(entity);
            return (FileDto) mapper.mapToDto(saved);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
