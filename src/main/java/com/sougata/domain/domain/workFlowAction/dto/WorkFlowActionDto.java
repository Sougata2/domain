package com.sougata.domain.domain.workFlowAction.dto;

import com.sougata.domain.domain.status.entity.StatusEntity;
import com.sougata.domain.domain.workFlowAction.enums.WorkFlowMovement;
import com.sougata.domain.role.entity.RoleEntity;
import com.sougata.domain.shared.MasterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.sougata.domain.domain.workFlowAction.entity.WorkFlowActionEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkFlowActionDto implements Serializable, MasterDto {
    private Long id;
    private String name;
    private StatusEntity status;
    private RoleEntity targetRole;
    private StatusEntity targetStatus;
    private WorkFlowMovement movement;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}