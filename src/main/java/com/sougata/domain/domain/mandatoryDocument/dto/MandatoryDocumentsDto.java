package com.sougata.domain.domain.mandatoryDocument.dto;

import com.sougata.domain.domain.forms.entity.FormEntity;
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
    private FormEntity form;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}