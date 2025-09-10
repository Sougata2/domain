package com.sougata.domain.domain.jobWorkFlowHistory.dto;

import com.sougata.domain.domain.job.dto.JobDto;
import com.sougata.domain.domain.status.dto.StatusDto;
import com.sougata.domain.domain.workFlowAction.enums.WorkFlowMovement;
import com.sougata.domain.file.dto.FileDto;
import com.sougata.domain.role.entity.RoleEntity;
import com.sougata.domain.shared.MasterDto;
import com.sougata.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.sougata.domain.domain.jobWorkFlowHistory.entity.JobWorkFlowHistoryEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobWorkFlowHistoryDto implements Serializable, MasterDto {
    private Long id;
    private JobDto job;
    private UserDto assigner;
    private UserDto assignee;
    private StatusDto status;
    private FileDto file;
    private String comments;
    private RoleEntity targetRole;
    private WorkFlowMovement movement;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}