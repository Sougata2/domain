package com.sougata.domain.domain.viewComponent.dto;

import com.sougata.domain.role.dto.RoleDto;
import com.sougata.domain.shared.MasterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link com.sougata.domain.domain.viewComponent.entity.ViewComponentEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewComponentDto implements Serializable, MasterDto {
    private Long id;
    private String name;
    private String applicationType;
    private Set<RoleDto> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}