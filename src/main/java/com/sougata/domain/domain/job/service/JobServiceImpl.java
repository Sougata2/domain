package com.sougata.domain.domain.job.service;

import com.sougata.domain.domain.devices.entity.DeviceEntity;
import com.sougata.domain.domain.devices.repository.DeviceRepository;
import com.sougata.domain.domain.job.dto.JobDto;
import com.sougata.domain.domain.job.entity.JobEntity;
import com.sougata.domain.domain.job.repository.JobRepository;
import com.sougata.domain.mapper.RelationalMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final DeviceRepository deviceRepository;
    private final JobRepository repository;
    private final RelationalMapper mapper;

    @Override
    public JobDto findJobByDeviceId(Long deviceId) {
        try {
            Optional<DeviceEntity> device = deviceRepository.findById(deviceId);
            if (device.isEmpty()) {
                throw new EntityNotFoundException("Device Entity with ID %d is not found".formatted(deviceId));
            }

            Optional<JobEntity> job = repository.findJobByDeviceId(deviceId);
            if (job.isEmpty()) {
                throw new EntityNotFoundException("Job for device ID %d does not exists".formatted(deviceId));
            }

            return (JobDto) mapper.mapToDto(job.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JobDto findById(Long id) {
        return null;
    }

    @Override
    public JobDto create(JobDto dto) {
        return null;
    }

    @Override
    public JobDto update(JobDto dto) {
        return null;
    }

    @Override
    public JobDto delete(JobDto dto) {
        return null;
    }
}
