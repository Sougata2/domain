package com.sougata.domain.domain.viewComponent.service.impl;

import com.sougata.domain.domain.viewComponent.dto.ViewComponentDto;
import com.sougata.domain.domain.viewComponent.entity.ViewComponentEntity;
import com.sougata.domain.domain.viewComponent.repository.ViewComponentRepository;
import com.sougata.domain.domain.viewComponent.service.ViewComponentService;
import com.sougata.domain.mapper.RelationalMapper;
import com.sougata.domain.role.entity.RoleEntity;
import com.sougata.domain.role.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ViewComponentServiceImpl implements ViewComponentService {
    private final ViewComponentRepository repository;
    private final RoleRepository roleRepository;
    private final RelationalMapper mapper;

    @Override
    public List<ViewComponentDto> findAll() {
        try {
            List<ViewComponentEntity> entities = repository.findAll();
            return entities.stream().map(e -> (ViewComponentDto) mapper.mapToDto(e)).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ViewComponentDto> findAllByRoleIdAndApplicationType(String role, String applicationType) {
        try {
            Optional<RoleEntity> roleEntity = roleRepository.findByRoleName(role);
            if (roleEntity.isEmpty()) {
                throw new EntityNotFoundException("Role Entity with name %s is not found!".formatted(role));
            }

            List<ViewComponentEntity> entities = repository.findAllByRoleIdAndApplicationType(roleEntity.get().getId(), applicationType);
            return entities.stream().map(e -> (ViewComponentDto) mapper.mapToDto(e)).toList();
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ViewComponentDto findById(Long id) {
        try {
            Optional<ViewComponentEntity> entity = repository.findById(id);
            if (entity.isEmpty()) {
                throw new EntityNotFoundException("ViewComponent with id %d is not found!".formatted(id));
            }
            return (ViewComponentDto) mapper.mapToDto(entity.get());
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public ViewComponentDto create(ViewComponentDto dto) {
        try {
            ViewComponentEntity entity = (ViewComponentEntity) mapper.mapToEntity(dto);
            ViewComponentEntity saved = repository.save(entity);
            return (ViewComponentDto) mapper.mapToDto(saved);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public ViewComponentDto update(ViewComponentDto dto) {
        try {
            Optional<ViewComponentEntity> og = repository.findById(dto.getId());
            if (og.isEmpty()) {
                throw new EntityNotFoundException("View Component with id %d is not found!".formatted(dto.getId()));
            }

            ViewComponentEntity nu = (ViewComponentEntity) mapper.mapToEntity(dto);
            ViewComponentEntity merged = (ViewComponentEntity) mapper.merge(nu, og.get());
            ViewComponentEntity saved = repository.save(merged);
            return (ViewComponentDto) mapper.mapToDto(saved);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public ViewComponentDto delete(ViewComponentDto dto) {
        try {
            Optional<ViewComponentEntity> og = repository.findById(dto.getId());
            if (og.isEmpty()) {
                throw new EntityNotFoundException("View Component with id %d is not found!".formatted(dto.getId()));
            }
            repository.delete(og.get());
            return dto;
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
