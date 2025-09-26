package com.sougata.domain.domain.labTestRecord.dto;

import com.sougata.domain.domain.job.dto.JobDto;
import com.sougata.domain.domain.labTestTemplate.dto.LabTestTemplateDto;
import com.sougata.domain.shared.MasterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.sougata.domain.domain.labTestRecord.entity.LabTestRecordEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LabTestRecordDto implements Serializable, MasterDto {
    private Long id;
    private LabTestTemplateDto template;
    private JobDto job;
    private String cellData;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}