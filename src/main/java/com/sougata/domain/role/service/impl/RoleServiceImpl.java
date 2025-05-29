package com.sougata.domain.role.service.impl;

import com.sougata.domain.mapper.RelationalMapper;
import com.sougata.domain.role.dto.RoleDto;
import com.sougata.domain.role.entity.RoleEntity;
import com.sougata.domain.role.repository.RoleRepository;
import com.sougata.domain.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    @Override
    public List<RoleDto> getAllRoles() {
        List<RoleEntity> entities = repository.findAllRoles();
        return entities.stream().map(e -> (RoleDto) RelationalMapper.mapToDto(e)).toList();
    }
}
