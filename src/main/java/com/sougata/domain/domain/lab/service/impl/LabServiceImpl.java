package com.sougata.domain.domain.lab.service.impl;

import com.sougata.domain.domain.application.entity.ApplicationEntity;
import com.sougata.domain.domain.lab.dto.LabDto;
import com.sougata.domain.domain.lab.entity.LabEntity;
import com.sougata.domain.domain.lab.repository.LabRepository;
import com.sougata.domain.domain.lab.service.LabService;
import com.sougata.domain.mapper.RelationalMapper;
import com.sougata.domain.user.entity.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LabServiceImpl implements LabService {
    private final LabRepository repository;
    private final RelationalMapper mapper;

    @Override
    public List<LabDto> findAll() {
        try {
            List<LabEntity> list = repository.findAll();
            return list.stream().map(e -> (LabDto) mapper.mapToDto(e)).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LabDto findById(Long id) {
        try {
            Optional<LabEntity> entity = repository.findById(id);
            if (entity.isEmpty()) {
                throw new EntityNotFoundException("Lab with id %s not found".formatted(id));
            }
            return (LabDto) mapper.mapToDto(entity.get());
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public LabDto create(LabDto dto) {
        try {
            LabEntity entity = (LabEntity) mapper.mapToEntity(dto);
            LabEntity saved = repository.save(entity);
            return (LabDto) mapper.mapToDto(saved);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public LabDto update(LabDto dto) {
        try {
            Optional<LabEntity> og = repository.findById(dto.getId());
            if (og.isEmpty()) {
                throw new EntityNotFoundException("Lab with id %s not found".formatted(dto.getId()));
            }
            LabEntity nu = (LabEntity) mapper.mapToEntity(dto);
            LabEntity merged = (LabEntity) mapper.merge(nu, og.get());
            LabEntity saved = repository.save(merged);
            return (LabDto) mapper.mapToDto(saved);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public LabDto delete(LabDto dto) {
        try {
            Optional<LabEntity> entity = repository.findById(dto.getId());
            if (entity.isEmpty()) {
                throw new EntityNotFoundException("Lab with id %s not found".formatted(dto.getId()));
            }

            // detach relations
            if (!entity.get().getApplications().isEmpty()) {
                for (ApplicationEntity application : entity.get().getApplications()) {
                    application.setLab(null);
                }
                entity.get().setApplications(new HashSet<>());
            }

            if (!entity.get().getUsers().isEmpty()) {
                for (UserEntity user : entity.get().getUsers()) {
                    user.setLab(null);
                }
                entity.get().setUsers(new HashSet<>());
            }

            repository.delete(entity.get());
            return dto;
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
