package com.sougata.domain.domain.certifications.dto;

import com.sougata.domain.domain.application.dto.ApplicationDto;
import com.sougata.domain.file.dto.FileDto;
import com.sougata.domain.shared.MasterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.sougata.domain.domain.certifications.entity.CertificationEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CertificationDto implements Serializable, MasterDto {
    private Long id;
    private String name;
    private String certificateNumber;
    private String description;
    private FileDto file;
    private ApplicationDto application;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}