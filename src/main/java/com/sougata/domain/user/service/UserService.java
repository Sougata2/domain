package com.sougata.domain.user.service;

import com.sougata.domain.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAllUsersWithRolesAndDefaultRole();

    UserDto createUser(UserDto dto);

    UserDto updateUser(UserDto dto);

    UserDto findUserById(Long id);

    UserDto deleteUser(UserDto dto);
}
