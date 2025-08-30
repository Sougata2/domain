package com.sougata.domain.domain.activity.service.impl;

import com.sougata.domain.domain.activity.dto.ActivityDto;
import com.sougata.domain.domain.activity.entity.ActivityEntity;
import com.sougata.domain.domain.activity.repository.ActivityRepository;
import com.sougata.domain.domain.activity.service.ActivityService;
import com.sougata.domain.domain.devices.entity.DeviceEntity;
import com.sougata.domain.domain.specification.entity.SpecificationEntity;
import com.sougata.domain.domain.subService.entity.SubServiceEntity;
import com.sougata.domain.domain.subService.repository.SubServiceRepository;
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
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository repository;
    private final SubServiceRepository subServiceRepository;
    private final RelationalMapper mapper;


    @Override
    public List<ActivityDto> findAll() {
        List<ActivityEntity> list = repository.findAll();
        return list.stream().map(e -> (ActivityDto) mapper.mapToDto(e)).toList();
    }

    @Override
    public List<ActivityDto> findBySubServiceId(Long subServiceId) {
        try {
            Optional<SubServiceEntity> subServiceEntity = subServiceRepository.findById(subServiceId);
            if (subServiceEntity.isEmpty()) {
                throw new EntityNotFoundException("Sub Service with id %d Not Found".formatted(subServiceId));
            }
            List<ActivityEntity> list = repository.findBySubServiceId(subServiceId);
            return list.stream().map(e -> (ActivityDto) mapper.mapToDto(e)).toList();
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ActivityDto findById(Long id) {
        Optional<ActivityEntity> entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new EntityNotFoundException("Activity entity with id %d not found".formatted(id));
        }
        return (ActivityDto) mapper.mapToDto(entity.get());
    }

    @Override
    @Transactional
    public ActivityDto create(ActivityDto dto) {
        ActivityEntity entity = (ActivityEntity) mapper.mapToEntity(dto);
        ActivityEntity saved = repository.save(entity);
        return (ActivityDto) mapper.mapToDto(saved);
    }

    @Override
    @Transactional
    public ActivityDto update(ActivityDto dto) {
        Optional<ActivityEntity> og = repository.findById(dto.getId());
        if (og.isEmpty()) {
            throw new EntityNotFoundException("Activity entity with id %d not found".formatted(dto.getId()));
        }
        ActivityEntity nu = (ActivityEntity) mapper.mapToEntity(dto);
        ActivityEntity merged = (ActivityEntity) mapper.merge(nu, og.get());
        ActivityEntity updated = repository.save(merged);
        return (ActivityDto) mapper.mapToDto(updated);
    }

    @Override
    @Transactional
    public ActivityDto delete(ActivityDto dto) {
        Optional<ActivityEntity> og = repository.findById(dto.getId());
        if (og.isEmpty()) {
            throw new EntityNotFoundException("Activity entity with id %d not found".formatted(dto.getId()));
        }

        // detach the relations.
        if (!og.get().getSpecifications().isEmpty()) {
            for (SpecificationEntity specification : og.get().getSpecifications()) {
                specification.getActivities().remove(og.get());
            }
            og.get().setSpecifications(new HashSet<>());
        }

        if (!og.get().getDevices().isEmpty()) {
            for (DeviceEntity device : og.get().getDevices()) {
                device.getActivities().remove(og.get());
            }
            og.get().setDevices(new HashSet<>());
        }

        if (!og.get().getSubServices().isEmpty()) {
            for (SubServiceEntity subService : og.get().getSubServices()) {
                subService.getActivities().remove(og.get());
            }
            og.get().setSubServices(new HashSet<>());
        }

        repository.delete(og.get());
        return dto;
    }
}
