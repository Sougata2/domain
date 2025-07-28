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
    private FormDto form;
    private MenuDto menu;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "FormStageDto{" +
                "id=" + id +
                ", menu=" + (menu != null ? menu.getName() : "") +
                ", order=" + (stageOrder != null ? stageOrder : "") +
                ", form=" + (form != null ? form.getName() : "null") +
                ", createdAt=" + (createdAt != null ? createdAt.toString() : "null") +
                ", updatedAt=" + (updatedAt != null ? updatedAt.toString() : "null") +
                '}';
    }
}