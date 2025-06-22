package com.sougata.domain.user.service.impl;

import com.sougata.domain.mapper.RelationalMapper;
import com.sougata.domain.role.dto.RoleDto;
import com.sougata.domain.role.entity.RoleEntity;
import com.sougata.domain.user.dto.UserDto;
import com.sougata.domain.user.entity.UserEntity;
import com.sougata.domain.user.repository.UserRepository;
import com.sougata.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RelationalMapper mapper;
    private final UserRepository repository;

    @Override
    public List<UserDto> findAllUsersWithRolesAndDefaultRole() {
        List<UserEntity> entities = repository.findAllUsersWithRolesAndDefaultRole();
        return entities.stream().map(e -> (UserDto) mapper.mapToDto(e)).toList();
    }

    @Override
    public UserDto createUser(UserDto dto) {
        UserEntity entity = (UserEntity) mapper.mapToEntity(dto);
        UserEntity created = repository.save(entity);
        return (UserDto) mapper.mapToDto(created);
    }

    @Override
    public UserDto updateUser(UserDto dto) {
        Optional<UserEntity> og = repository.findById(dto.getId());
        if (og.isEmpty()) {
            return null;
        }
        UserEntity nu = (UserEntity) mapper.mapToEntity(dto);
        System.out.println("*************************");
        System.out.println("og = " + og.get());
        System.out.println("nu = " + nu);
        UserEntity merged = (UserEntity) mapper.merge(nu, og.get());
        System.out.println("merged = " + merged);
        System.out.println("*************************");

        UserEntity updated = repository.save(merged);
        return (UserDto) mapper.mapToDto(updated);
    }

    @Override
    public UserDto findUserById(Long id) {
        UserEntity entity = repository.findById(id).orElse(null);
        return (UserDto) mapper.mapToDto(entity);
    }

    @Override
    @Transactional
    public UserDto deleteUser(UserDto dto) {
        Optional<UserEntity> og = repository.findById(dto.getId());
        if (og.isEmpty()) {
            return null;
        }

        // detaching relations
        UserEntity user = og.get();
        for (RoleEntity role : user.getRoles()) {
            role.getUsers().remove(user);
        }
        user.setRoles(new HashSet<>());

        if (user.getDefaultRole() != null) {
            user.getDefaultRole().getDefaultRoleUsers().remove(user);
            user.setDefaultRole(null);
        }


        UserDto result = (UserDto) mapper.mapToDto(user);
        repository.delete(user);
        return result;
    }

    @Override
    public RoleDto getDefaultRoleForUser(Long userId) {
        Optional<UserEntity> og = repository.findById(userId);
        if (og.isEmpty()) return null;
        RoleEntity defaultRole = repository.findDefaultRoleForUser(userId);
        return (RoleDto) mapper.mapToDto(defaultRole);
    }
}
