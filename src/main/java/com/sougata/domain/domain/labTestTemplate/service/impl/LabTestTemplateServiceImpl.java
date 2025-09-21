package com.sougata.domain.domain.labTestTemplate.service.impl;

import com.sougata.domain.domain.job.entity.JobEntity;
import com.sougata.domain.domain.job.repository.JobRepository;
import com.sougata.domain.domain.labTestTemplate.dto.LabTestTemplateDto;
import com.sougata.domain.domain.labTestTemplate.entity.LabTestTemplateEntity;
import com.sougata.domain.domain.labTestTemplate.repository.LabTestTemplateRepository;
import com.sougata.domain.domain.labTestTemplate.service.LabTestTemplateService;
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
public class LabTestTemplateServiceImpl implements LabTestTemplateService {
    private final SubServiceRepository subServiceRepository;
    private final LabTestTemplateRepository repository;
    private final JobRepository jobRepository;
    private final RelationalMapper mapper;

    @Override
    public List<LabTestTemplateDto> findByJobId(Long jobId) {
        try {
            Optional<JobEntity> job = jobRepository.findById(jobId);
            if (job.isEmpty()) {
                throw new EntityNotFoundException("Job Entity with ID : %d is not found".formatted(jobId));
            }
            List<LabTestTemplateEntity> entities = repository.findByJobId(jobId);
            return entities.stream().map(e -> (LabTestTemplateDto) mapper.mapToDto(e)).toList();
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<LabTestTemplateDto> findBySubServiceId(Long subServiceId) {
        try {
            Optional<SubServiceEntity> subService = subServiceRepository.findById(subServiceId);
            if (subService.isEmpty()) {
                throw new EntityNotFoundException("SubService Entity with ID : %d is not found".formatted(subServiceId));
            }
            List<LabTestTemplateEntity> entities = repository.findBySubServiceId(subServiceId);
            return entities.stream().map(e -> (LabTestTemplateDto) mapper.mapToDto(e)).toList();
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<LabTestTemplateDto> findAll() {
        try {
            List<LabTestTemplateEntity> entities = repository.findAll();
            return entities.stream().map(e -> (LabTestTemplateDto) mapper.mapToDto(e)).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LabTestTemplateDto findById(Long id) {
        try {
            Optional<LabTestTemplateEntity> entity = repository.findById(id);
            if (entity.isEmpty()) {
                throw new EntityNotFoundException("Lab Test Template Entity with ID : %d is not found".formatted(id));
            }
            return (LabTestTemplateDto) mapper.mapToDto(entity.get());
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public LabTestTemplateDto create(LabTestTemplateDto dto) {
        try {
            LabTestTemplateEntity entity = (LabTestTemplateEntity) mapper.mapToEntity(dto);
            LabTestTemplateEntity saved = repository.save(entity);
            return (LabTestTemplateDto) mapper.mapToDto(saved);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public LabTestTemplateDto update(LabTestTemplateDto dto) {
        try {
            Optional<LabTestTemplateEntity> og = repository.findById(dto.getId());
            if (og.isEmpty()) {
                throw new EntityNotFoundException("Lab Test Template Entity with ID : %d is not found".formatted(dto.getId()));
            }
            LabTestTemplateEntity nu = (LabTestTemplateEntity) mapper.mapToEntity(dto);
            LabTestTemplateEntity merged = (LabTestTemplateEntity) mapper.merge(nu, og.get());
            LabTestTemplateEntity saved = repository.save(merged);
            return (LabTestTemplateDto) mapper.mapToDto(saved);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public LabTestTemplateDto delete(LabTestTemplateDto dto) {
        try {
            Optional<LabTestTemplateEntity> entity = repository.findById(dto.getId());
            if (entity.isEmpty()) {
                throw new EntityNotFoundException("Lab Test Template Entity with ID : %d is not found".formatted(dto.getId()));
            }

            // detach relations
            if (!entity.get().getSubServices().isEmpty()) {
                for (SubServiceEntity subService : entity.get().getSubServices()) {
                    subService.getTestTemplates().remove(entity.get());
                }
                entity.get().setSubServices(new HashSet<>());
            }

            repository.delete(entity.get());
            return dto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
