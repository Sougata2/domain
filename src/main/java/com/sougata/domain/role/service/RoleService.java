package com.sougata.domain.role.service;

import com.sougata.domain.role.dto.RoleDto;

import java.util.List;

public interface RoleService {
    List<RoleDto> getAllRoles();

    RoleDto updateRole(RoleDto roleDto);

    RoleDto createRole(RoleDto dto);

    RoleDto deleteRole(RoleDto dto);

    RoleDto getRoleById(Long roleId);
}
