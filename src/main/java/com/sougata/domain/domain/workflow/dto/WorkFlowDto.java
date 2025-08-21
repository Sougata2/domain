package com.sougata.domain.domain.workflow.dto;

import com.sougata.domain.domain.application.dto.ApplicationDto;
import com.sougata.domain.domain.status.dto.StatusDto;
import com.sougata.domain.shared.MasterDto;
import com.sougata.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.sougata.domain.domain.workflow.entity.WorkFlowEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkFlowDto implements Serializable, MasterDto {
    private Long id;
    private ApplicationDto application;
    private UserDto assigner;
    private UserDto assignee;
    private StatusDto status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}