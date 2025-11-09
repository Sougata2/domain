package com.sougata.domain.domain.status.dto;

import com.sougata.domain.domain.application.dto.ApplicationDto;
import com.sougata.domain.domain.job.dto.JobDto;
import com.sougata.domain.domain.jobWorkFlowHistory.dto.JobWorkFlowHistoryDto;
import com.sougata.domain.domain.viewComponent.dto.ViewComponentDto;
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
    private Boolean isFinal;
    private String postDescription;
    private String applicationType;
    private String actionType;
    private Set<JobDto> jobs;
    private Set<ApplicationDto> applications;
    private Set<WorkFlowHistoryDto> workFlowHistory;
    private Set<JobWorkFlowHistoryDto> jobWorkFlowHistory;
    private Set<WorkFlowActionDto> actions;
    private Set<WorkFlowActionDto> targetStatusActions;
    private Set<ViewComponentDto> components;
    private LocalDateTime createdAt;
}