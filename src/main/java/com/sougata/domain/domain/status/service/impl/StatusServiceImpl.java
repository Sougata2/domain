package com.sougata.domain.domain.status.service.impl;

import com.sougata.domain.domain.application.entity.ApplicationEntity;
import com.sougata.domain.domain.status.dto.StatusDto;
import com.sougata.domain.domain.status.entity.StatusEntity;
import com.sougata.domain.domain.status.repository.StatusRepository;
import com.sougata.domain.domain.status.service.StatusService;
import com.sougata.domain.mapper.Mapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {
    private final StatusRepository repository;
    private final Mapper mapper;

    @Override
    public List<StatusDto> findAll() {
        List<StatusEntity> list = repository.findAll();
        return list.stream().map(e -> (StatusDto) mapper.toDto(e)).toList();
    }

    @Override
    public StatusDto findById(Long id) {
        Optional<StatusEntity> entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new EntityNotFoundException("Status with id %d is not found".formatted(id));
        }
        return (StatusDto) mapper.toDto(entity.get());
    }

    @Override
    @Transactional
    public StatusDto create(StatusDto dto) {
        StatusEntity entity = (StatusEntity) mapper.toEntity(dto);
        StatusEntity saved = repository.save(entity);
        return (StatusDto) mapper.toDto(saved);
    }

    @Override
    @Transactional
    public StatusDto update(StatusDto dto) {
        Optional<StatusEntity> og = repository.findById(dto.getId());
        if (og.isEmpty()) {
            throw new EntityNotFoundException("Status with id %d is not found".formatted(dto.getId()));
        }

        StatusEntity nu = (StatusEntity) mapper.toEntity(dto);
        StatusEntity merged = (StatusEntity) mapper.merge(nu, og.get());
        StatusEntity saved = repository.save(merged);
        return (StatusDto) mapper.toDto(saved);
    }

    @Override
    @Transactional
    public StatusDto delete(StatusDto dto) {
        Optional<StatusEntity> entity = repository.findById(dto.getId());
        if (entity.isEmpty()) {
            throw new EntityNotFoundException("Status with id %d is not found".formatted(dto.getId()));
        }

        // detach relations
        if (!entity.get().getApplications().isEmpty()) {
            for (ApplicationEntity application : entity.get().getApplications()) {
                application.setStatus(null);
            }
            entity.get().setApplications(new HashSet<>());
        }

        repository.delete(entity.get());
        return dto;
    }
}
