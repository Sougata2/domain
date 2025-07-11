package com.sougata.domain.file.dto;

import com.sougata.domain.domain.document.dto.DocumentDto;
import com.sougata.domain.shared.MasterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.sougata.domain.file.entity.FileEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDto implements Serializable, MasterDto {
    private Long id;
    private String name;
    private String extension;
    private String checksum;
    private Long size;
    private DocumentDto document;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}