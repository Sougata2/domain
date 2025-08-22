package com.sougata.domain.domain.status.dto;

import com.sougata.domain.domain.application.dto.ApplicationDto;
import com.sougata.domain.domain.workFlowAction.dto.WorkFlowActionDto;
import com.sougata.domain.domain.workflowHistory.dto.WorkFlowHistoryDto;
import com.sougata.domain.shared.MasterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link com.sougata.domain.domain.status.entity.StatusEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusDto implements Serializable, MasterDto {
    private Long id;
    private String name;
    private String description;
    private Set<ApplicationDto> applications;
    private Set<WorkFlowHistoryDto> workFlowHistory;
    private Set<WorkFlowActionDto> actions;
    private Set<WorkFlowActionDto> targetStatusActions;
    private LocalDateTime createdAt;
}