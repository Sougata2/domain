package com.sougata.domain.domain.workflowHistory.dto;

import com.sougata.domain.domain.application.dto.ApplicationDto;
import com.sougata.domain.domain.status.dto.StatusDto;
import com.sougata.domain.domain.workFlowAction.enums.WorkFlowMovement;
import com.sougata.domain.domain.workflowHistory.entity.WorkFlowHistoryEntity;
import com.sougata.domain.role.dto.RoleDto;
import com.sougata.domain.shared.MasterDto;
import com.sougata.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link WorkFlowHistoryEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkFlowHistoryDto implements Serializable, MasterDto {
    private Long id;
    private ApplicationDto application;
    private UserDto assigner;
    private UserDto assignee;
    private StatusDto status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private WorkFlowMovement movement;
    private RoleDto targetRole;
    private String comments;
}