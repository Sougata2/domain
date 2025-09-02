package com.sougata.domain.user.service;

import com.sougata.domain.role.dto.RoleDto;
import com.sougata.domain.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAllUsersWithRolesAndDefaultRole();

    List<UserDto> findByDefaultRoleIdAndLabId(Long defaultRoleId, Long labId);

    UserDto removeRole(Long userId, Long roleId);

    UserDto createUser(UserDto dto);

    UserDto updateUser(UserDto dto);

    UserDto findUserById(Long id);

    UserDto deleteUser(UserDto dto);


    RoleDto getDefaultRoleForUser(Long userId);
}
