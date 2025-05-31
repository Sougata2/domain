package com.sougata.domain.role.service.impl;

import com.sougata.domain.mapper.RelationalMapper;
import com.sougata.domain.role.dto.RoleDto;
import com.sougata.domain.role.entity.RoleEntity;
import com.sougata.domain.role.repository.RoleRepository;
import com.sougata.domain.role.service.RoleService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final EntityManager entityManager;
    private final RoleRepository repository;

    @Override
    public List<RoleDto> getAllRoles() {
        List<RoleEntity> entities = repository.findAllRoles();
        return entities.stream().map(e -> (RoleDto) RelationalMapper.mapToDto(e)).toList();
    }

    @Override
    public RoleDto updateRole(RoleDto roleDto) {
        Optional<RoleEntity> og = repository.findById(roleDto.getId());
        if (og.isEmpty()) return null;
        RoleEntity nu = (RoleEntity) RelationalMapper.mapToEntity(roleDto);
        RoleEntity merged = (RoleEntity) RelationalMapper.merge(nu, og.get(), entityManager);
        RoleEntity updated = repository.save(merged);
        return (RoleDto) RelationalMapper.mapToDto(updated);
    }
}
