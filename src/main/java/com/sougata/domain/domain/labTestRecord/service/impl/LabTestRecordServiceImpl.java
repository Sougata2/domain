package com.sougata.domain.domain.labTestRecord.service.impl;

import com.sougata.domain.domain.job.entity.JobEntity;
import com.sougata.domain.domain.job.repository.JobRepository;
import com.sougata.domain.domain.labTestRecord.dto.LabTestRecordDto;
import com.sougata.domain.domain.labTestRecord.entity.LabTestRecordEntity;
import com.sougata.domain.domain.labTestRecord.repository.LabTestRecordRepository;
import com.sougata.domain.domain.labTestRecord.service.LabTestRecordService;
import com.sougata.domain.domain.labTestTemplate.entity.LabTestTemplateEntity;
import com.sougata.domain.domain.labTestTemplate.repository.LabTestTemplateRepository;
import com.sougata.domain.mapper.RelationalMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LabTestRecordServiceImpl implements LabTestRecordService {
    private final LabTestTemplateRepository templateRepository;
    private final LabTestRecordRepository repository;
    private final JobRepository jobRepository;
    private final RelationalMapper mapper;

    @Override
    public LabTestRecordDto findByTemplateIdAndJobId(Long templateId, Long jobId) {
        try {
            Optional<LabTestTemplateEntity> template = templateRepository.findById(templateId);
            if (template.isEmpty()) {
                throw new EntityNotFoundException("Lab Test Template Entity with ID : %d is not found".formatted(templateId));
            }

            Optional<JobEntity> job = jobRepository.findById(jobId);
            if (job.isEmpty()) {
                throw new EntityNotFoundException("Job Entity with ID : %d is not found".formatted(jobId));
            }

            Optional<LabTestRecordEntity> entity = repository.findByJobIdAndTemplateId(jobId, templateId);
            if (entity.isEmpty()) {
                throw new EntityNotFoundException("No Lab Test Record found for job:%d & template:%d".formatted(jobId, templateId));
            }

            return (LabTestRecordDto) mapper.mapToDto(entity.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LabTestRecordDto findById(Long id) {
        try {
            Optional<LabTestTemplateEntity> entity = templateRepository.findById(id);
            if (entity.isEmpty()) {
                throw new EntityNotFoundException("Lab Test Template Entity with ID : %d is not found".formatted(id));
            }
            return (LabTestRecordDto) mapper.mapToDto(entity.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public LabTestRecordDto create(LabTestRecordDto dto) {
        try {
            Optional<LabTestTemplateEntity> template = templateRepository.findById(dto.getTemplate().getId());
            if (template.isEmpty()) {
                throw new EntityNotFoundException("Lab Test Template Entity with ID : %d is not found".formatted(dto.getTemplate().getId()));
            }

            Optional<JobEntity> job = jobRepository.findById(dto.getJob().getId());
            if (job.isEmpty()) {
                throw new EntityNotFoundException("Job Entity with ID : %d is not found".formatted(dto.getJob().getId()));
            }

            LabTestRecordEntity entity = (LabTestRecordEntity) mapper.mapToEntity(dto);
            entity.setTemplate(template.get());
            entity.setJob(job.get());
            LabTestRecordEntity saved = repository.save(entity);
            return (LabTestRecordDto) mapper.mapToDto(saved);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LabTestRecordDto update(LabTestRecordDto dto) {
        return null;
    }

    @Override
    public LabTestRecordDto delete(LabTestRecordDto dto) {
        return null;
    }
}
