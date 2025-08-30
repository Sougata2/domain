package com.sougata.domain.domain.workFlowGroup.dto;

import com.sougata.domain.domain.subService.dto.SubServiceDto;
import com.sougata.domain.domain.workFlowAction.dto.WorkFlowActionDto;
import com.sougata.domain.shared.MasterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link com.sougata.domain.domain.workFlowGroup.entity.WorkFlowGroupEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkFlowGroupDto implements Serializable, MasterDto {
    private Long id;
    private String name;
    private Set<SubServiceDto> subServices;
    private Set<WorkFlowActionDto> actions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "WorkFlowGroupDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subServices=" + (subServices.isEmpty() ? "null" : subServices.stream().map(s -> s.getName() == null ? "null" : s.getName())) +
                ", actions=" + (actions.isEmpty() ? "null" : actions.stream().map(s -> s.getName() == null ? "null" : s.getName())) +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}