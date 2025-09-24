package com.sougata.domain.domain.subService.dto;

import com.sougata.domain.domain.activity.dto.ActivityDto;
import com.sougata.domain.domain.application.dto.ApplicationDto;
import com.sougata.domain.domain.forms.dto.FormDto;
import com.sougata.domain.domain.labTestTemplate.dto.LabTestTemplateDto;
import com.sougata.domain.domain.mandatoryDocument.dto.MandatoryDocumentsDto;
import com.sougata.domain.domain.services.dto.ServiceDto;
import com.sougata.domain.domain.subService.entity.SubServiceEntity;
import com.sougata.domain.domain.workFlowGroup.dto.WorkFlowGroupDto;
import com.sougata.domain.shared.MasterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link SubServiceEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubServiceDto implements Serializable, MasterDto {
    private Long id;
    private String name;
    private Set<ServiceDto> services;
    private FormDto form;
    private LocalDateTime createdAt;
    private Set<ActivityDto> activities;
    private Set<ApplicationDto> applications;
    private Set<MandatoryDocumentsDto> mandatoryDocuments;
    private Set<LabTestTemplateDto> testTemplates;
    private WorkFlowGroupDto workFlowGroup;
}