package com.sougata.domain.domain.formStages.dto;

import com.sougata.domain.domain.forms.dto.FormDto;
import com.sougata.domain.menu.dto.MenuDto;
import com.sougata.domain.shared.MasterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link com.sougata.domain.domain.formStages.entity.FormStageEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormStageDto implements Serializable, MasterDto {
    private Long id;
    private Integer stageOrder;
    private Set<FormDto> forms;
    private MenuDto menu;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "FormStageDto{" +
                "id=" + id +
                ", name='" + (name != null ? name : "") + '\'' +
                ", url='" + (url != null ? url : "") + '\'' +
                ", order=" + (stageOrder != null ? stageOrder : "") +
                ", forms=" + (forms != null ? forms.stream()
                .map(f -> f.getId() != null ? f.getId().toString() : "null")
                .toList()
                : "[]") +
                ", createdAt=" + (createdAt != null ? createdAt.toString() : "null") +
                ", updatedAt=" + (updatedAt != null ? updatedAt.toString() : "null") +
                '}';
    }
}