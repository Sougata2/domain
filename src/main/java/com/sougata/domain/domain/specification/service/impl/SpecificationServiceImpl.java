package com.sougata.domain.domain.specification.service.impl;

import com.sougata.domain.domain.activity.entity.ActivityEntity;
import com.sougata.domain.domain.activity.repository.ActivityRepository;
import com.sougata.domain.domain.devices.entity.DeviceEntity;
import com.sougata.domain.domain.specification.dto.SpecificationDto;
import com.sougata.domain.domain.specification.entity.SpecificationEntity;
import com.sougata.domain.domain.specification.repository.SpecificationRepository;
import com.sougata.domain.domain.specification.service.SpecificationService;
import com.sougata.domain.mapper.RelationalMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpecificationServiceImpl implements SpecificationService {
    private final RelationalMapper mapper;
    private final SpecificationRepository repository;
    private final ActivityRepository activityRepository;


    @Override
    public List<SpecificationDto> findAll() {
        List<SpecificationEntity> entities = repository.findAll();
        return entities.stream().map(e -> (SpecificationDto) mapper.mapToDto(e)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpecificationDto> findByActivityId(Long id) {
        try {
            Optional<ActivityEntity> activity = activityRepository.findById(id);
            if (activity.isEmpty()) {
                throw new EntityNotFoundException("Activity with id %d not found".formatted(id));
            }
            List<SpecificationEntity> specifications = repository.findByActivityId(activity.get().getId());
            return specifications.stream().map(e -> (SpecificationDto) mapper.mapToDto(e)).toList();
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SpecificationDto findById(Long id) {
        try {
            Optional<SpecificationEntity> specification = repository.findById(id);
            if (specification.isEmpty()) {
                throw new EntityNotFoundException("Specification with id %d not found".formatted(id));
            }
            return (SpecificationDto) mapper.mapToDto(specification.get());
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public SpecificationDto create(SpecificationDto dto) {
        try {
            SpecificationEntity specification = (SpecificationEntity) mapper.mapToEntity(dto);
            SpecificationEntity saved = repository.save(specification);
            return (SpecificationDto) mapper.mapToDto(saved);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public SpecificationDto update(SpecificationDto dto) {
        try {
            Optional<SpecificationEntity> og = repository.findById(dto.getId());
            if (og.isEmpty()) {
                throw new EntityNotFoundException("Specification with id %d not found".formatted(dto.getId()));
            }
            SpecificationEntity nu = (SpecificationEntity) mapper.mapToEntity(dto);
            SpecificationEntity merged = (SpecificationEntity) mapper.merge(nu, og.get());
            SpecificationEntity saved = repository.save(merged);
            return (SpecificationDto) mapper.mapToDto(saved);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public SpecificationDto delete(SpecificationDto dto) {
        try {
            Optional<SpecificationEntity> entity = repository.findById(dto.getId());
            if (entity.isEmpty()) {
                throw new EntityNotFoundException("Specification with id %d not found".formatted(dto.getId()));
            }

            // detach relations
            if (!entity.get().getActivities().isEmpty()) {
                for (ActivityEntity activity : entity.get().getActivities()) {
                    activity.getSpecifications().remove(entity.get());
                }
                entity.get().setActivities(new HashSet<>());
            }

            if (!entity.get().getDevices().isEmpty()) {
                for (DeviceEntity device : entity.get().getDevices()) {
                    device.getSpecifications().remove(entity.get());
                }
                entity.get().setDevices(new HashSet<>());
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
