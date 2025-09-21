package com.sougata.domain.domain.labTestTemplate.dto;

import com.sougata.domain.domain.subService.dto.SubServiceDto;
import com.sougata.domain.shared.MasterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.sougata.domain.domain.labTestTemplate.entity.LabTestTemplateEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LabTestTemplateDto implements Serializable, MasterDto {
    private Long id;
    private String name;
    private String header;
    private String mergeData;
    private List<SubServiceDto> subServices;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}