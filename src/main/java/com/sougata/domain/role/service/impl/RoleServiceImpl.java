package com.sougata.domain.role.service.impl;

import com.sougata.domain.domain.workFlowAction.entity.WorkFlowActionEntity;
import com.sougata.domain.mapper.RelationalMapper;
import com.sougata.domain.menu.entity.MenuEntity;
import com.sougata.domain.role.dto.RoleDto;
import com.sougata.domain.role.entity.RoleEntity;
import com.sougata.domain.role.repository.RoleRepository;
import com.sougata.domain.role.service.RoleService;
import com.sougata.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RelationalMapper mapper;
    private final RoleRepository repository;

    @Override
    public List<RoleDto> getAllRoles() {
        List<RoleEntity> entities = repository.findAllRoles();
        return entities.stream().map(e -> (RoleDto) mapper.mapToDto(e)).toList();
    }

    @Override
    @Transactional
    public RoleDto updateRole(RoleDto roleDto) {
        Optional<RoleEntity> og = repository.findById(roleDto.getId());
        if (og.isEmpty()) return null;
        RoleEntity nu = (RoleEntity) mapper.mapToEntity(roleDto);
        RoleEntity merged = (RoleEntity) mapper.merge(nu, og.get());
        RoleEntity updated = repository.save(merged);
        return (RoleDto) mapper.mapToDto(updated);
    }

    @Override
    @Transactional
    public RoleDto createRole(RoleDto dto) {
        RoleEntity nu = (RoleEntity) mapper.mapToEntity(dto);
        RoleEntity saved = repository.save(nu);
        return (RoleDto) mapper.mapToDto(saved);
    }

    @Override
    @Transactional
    public RoleDto deleteRole(RoleDto dto) {
        Optional<RoleEntity> og = repository.findById(dto.getId());
        if (og.isEmpty()) return null;

        // detach relations
        if (!og.get().getMenus().isEmpty()) {
            for (MenuEntity menu : og.get().getMenus()) {
                menu.getRoles().remove(og.get());
            }
            og.get().setMenus(new HashSet<>());
        }

        if (!og.get().getUsers().isEmpty()) {
            for (UserEntity user : og.get().getUsers()) {
                user.getRoles().remove(og.get());
            }
            og.get().setUsers(new HashSet<>());
        }

        if (!og.get().getDefaultRoleUsers().isEmpty()) {
            for (UserEntity user : og.get().getDefaultRoleUsers()) {
                if (Objects.equals(user.getDefaultRole().getId(), og.get().getId())) {
                    user.setDefaultRole(null);
                }
            }
        }

        if (!og.get().getActions().isEmpty()) {
            for (WorkFlowActionEntity action : og.get().getActions()) {
                if (action.getTargetRole().getId().equals(og.get().getId())) {
                    action.setTargetRole(null);
                }
            }
            og.get().setActions(new HashSet<>());
        }

        repository.delete(og.get());
        return (RoleDto) mapper.mapToDto(og.get());
    }

    @Override
    public RoleDto getRoleById(Long roleId) {
        Optional<RoleEntity> og = repository.findById(roleId);
        return og.map(entity -> (RoleDto) mapper.mapToDto(entity)).orElse(null);
    }
}
