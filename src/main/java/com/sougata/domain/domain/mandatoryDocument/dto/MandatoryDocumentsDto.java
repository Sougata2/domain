package com.sougata.domain.domain.mandatoryDocument.dto;

import com.sougata.domain.domain.subService.dto.SubServiceDto;
import com.sougata.domain.shared.MasterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.sougata.domain.domain.mandatoryDocument.entity.MandatoryDocumentsEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MandatoryDocumentsDto implements Serializable, MasterDto {
    private Long id;
    private String name;
    private SubServiceDto subService;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}