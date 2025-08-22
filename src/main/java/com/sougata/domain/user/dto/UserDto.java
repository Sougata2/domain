package com.sougata.domain.user.dto;

import com.sougata.domain.domain.application.dto.ApplicationDto;
import com.sougata.domain.domain.workflowHistory.dto.WorkFlowHistoryDto;
import com.sougata.domain.role.dto.RoleDto;
import com.sougata.domain.shared.MasterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.sougata.domain.user.entity.UserEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable, MasterDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<RoleDto> roles;
    private RoleDto defaultRole;
    private Set<ApplicationDto> applications;
    private Set<ApplicationDto> assignments;
    private Set<WorkFlowHistoryDto> workFlowHistoryForAssigner;
    private Set<WorkFlowHistoryDto> workFlowHistoryForAssignee;
}