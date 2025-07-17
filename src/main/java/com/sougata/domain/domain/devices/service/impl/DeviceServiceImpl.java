package com.sougata.domain.domain.devices.service.impl;

import com.sougata.domain.domain.activity.entity.ActivityEntity;
import com.sougata.domain.domain.application.entity.ApplicationEntity;
import com.sougata.domain.domain.application.repository.ApplicationRepository;
import com.sougata.domain.domain.devices.dto.DeviceDto;
import com.sougata.domain.domain.devices.entity.DeviceEntity;
import com.sougata.domain.domain.devices.repository.DeviceRepository;
import com.sougata.domain.domain.devices.service.DeviceService;
import com.sougata.domain.domain.specification.entity.SpecificationEntity;
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
public class DeviceServiceImpl implements DeviceService {
    private final RelationalMapper mapper;
    private final DeviceRepository repository;
    private final ApplicationRepository applicationRepository;

    @Override
    public List<DeviceDto> findByReferenceNumber(String referenceNumber) {
        try {
            Optional<ApplicationEntity> application = applicationRepository.findByReferenceNumber(referenceNumber);
            if (application.isEmpty()) {
                throw new EntityNotFoundException("Application with reference number : %s not found".formatted(referenceNumber));
            }
            List<DeviceEntity> devices = repository.findByReferenceNumber(referenceNumber);
            return devices.stream().map(e -> (DeviceDto) mapper.mapToDto(e)).toList();
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DeviceDto findById(Long id) {
        Optional<DeviceEntity> device = repository.findById(id);
        if (device.isEmpty()) {
            throw new EntityNotFoundException("Device with id : %s not found".formatted(id));
        }
        return (DeviceDto) mapper.mapToDto(device.get());
    }

    @Override
    @Transactional
    public DeviceDto create(DeviceDto dto) {
        try {
            DeviceEntity entity = (DeviceEntity) mapper.mapToEntity(dto);
            DeviceEntity saved = repository.save(entity);
            return (DeviceDto) mapper.mapToDto(saved);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public DeviceDto update(DeviceDto dto) {
        Optional<DeviceEntity> og = repository.findById(dto.getId());
        if (og.isEmpty()) {
            throw new EntityNotFoundException("Device with id : %s not found".formatted(dto.getId()));
        }
        DeviceEntity nu = (DeviceEntity) mapper.mapToEntity(dto);
        DeviceEntity merged = (DeviceEntity) mapper.merge(nu, og.get());
        DeviceEntity saved = repository.save(merged);
        return (DeviceDto) mapper.mapToDto(saved);
    }

    @Override
    @Transactional
    public DeviceDto delete(DeviceDto dto) {
        Optional<DeviceEntity> entity = repository.findById(dto.getId());
        if (entity.isEmpty()) {
            throw new EntityNotFoundException("Device with id : %s not found".formatted(dto.getId()));
        }

        // detach relations
        if (!entity.get().getActivities().isEmpty()) {
            for (ActivityEntity activity : entity.get().getActivities()) {
                activity.getDevices().remove(entity.get());
            }
            entity.get().setActivities(new HashSet<>());
        }

        if (!entity.get().getSpecifications().isEmpty()) {
            for (SpecificationEntity specification : entity.get().getSpecifications()) {
                specification.getDevices().remove(entity.get());
            }
            entity.get().setSpecifications(new HashSet<>());
        }

        if (entity.get().getApplication() != null) {
            entity.get().getApplication().getDevices().remove(entity.get());
            entity.get().setApplication(null);
        }

        repository.delete(entity.get());
        return dto;
    }
}
