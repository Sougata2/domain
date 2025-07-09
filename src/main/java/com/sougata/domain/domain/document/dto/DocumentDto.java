package com.sougata.domain.domain.document.dto;

import com.sougata.domain.domain.application.entity.ApplicationEntity;
import com.sougata.domain.file.entity.FileEntity;
import com.sougata.domain.shared.MasterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link com.sougata.domain.domain.document.entity.DocumentEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDto implements Serializable, MasterDto {
    private Long id;
    private String name;
    private Set<FileEntity> files;
    private ApplicationEntity application;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}