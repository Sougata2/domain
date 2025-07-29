package com.sougata.domain.domain.forms.dto;

import com.sougata.domain.domain.formStages.dto.FormStageDto;
import com.sougata.domain.domain.forms.entity.FormEntity;
import com.sougata.domain.domain.mandatoryDocument.dto.MandatoryDocumentsDto;
import com.sougata.domain.shared.MasterDto;
import com.sougata.domain.subService.dto.SubServiceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link FormEntity}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormDto implements Serializable, MasterDto {
    private Long id;
    private String name;
    private Set<SubServiceDto> subServices;
    private Set<MandatoryDocumentsDto> mandatoryDocuments;
    private Set<FormStageDto> stages;
    private LocalDateTime createdAt;
}