package com.sougata.domain.domain.application.service.impl;

import com.sougata.domain.domain.application.dto.ApplicationDto;
import com.sougata.domain.domain.application.entity.ApplicationEntity;
import com.sougata.domain.domain.application.repository.ApplicationRepository;
import com.sougata.domain.domain.application.service.ApplicationService;
import com.sougata.domain.domain.devices.entity.DeviceEntity;
import com.sougata.domain.domain.document.entity.DocumentEntity;
import com.sougata.domain.domain.services.entity.ServiceEntity;
import com.sougata.domain.domain.services.repository.ServiceRepository;
import com.sougata.domain.mapper.RelationalMapper;
import com.sougata.domain.subService.entity.SubServiceEntity;
import com.sougata.domain.subService.repository.SubServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository repository;
    private final RelationalMapper mapper;
    private final ServiceRepository serviceRepository;
    private final SubServiceRepository subServiceRepository;

    @Override
    public List<ApplicationDto> findAll() {
        List<ApplicationEntity> list = repository.findAll();
        return list.stream().map(e -> (ApplicationDto) mapper.mapToDto(e)).toList();
    }

    @Override
    public ApplicationDto findById(Long id) {
        Optional<ApplicationEntity> entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new EntityNotFoundException("Application Entity with id %d not found".formatted(id));
        }
        return (ApplicationDto) mapper.mapToDto(entity.get());
    }

    @Override
    public ApplicationDto findByReferenceNumber(String referenceNumber) {
        Optional<ApplicationEntity> entity = repository.findByReferenceNumber(referenceNumber);
        if (entity.isEmpty()) {
            throw new EntityNotFoundException("Application Entity with reference number %s not found".formatted(referenceNumber));
        }
        return (ApplicationDto) mapper.mapToDto(entity.get());
    }

    @Override
    @Transactional
    public ApplicationDto create(ApplicationDto dto) throws Exception {
        // generating reference number
        String referenceNumber = generateReferenceNumber(dto);
        dto.setReferenceNumber(referenceNumber);
        // generating reference number

        ApplicationEntity entity = (ApplicationEntity) mapper.mapToEntity(dto);

        // set the reference to service
        Optional<ServiceEntity> serviceEntity = serviceRepository.findById(entity.getService().getId());
        if (serviceEntity.isEmpty()) {
            throw new EntityNotFoundException("Service Entity with id %d not found".formatted(entity.getService().getId()));
        }
        entity.setService(serviceEntity.get());

        // set the reference to subService
        Optional<SubServiceEntity> subServiceEntity = subServiceRepository.findById(entity.getSubService().getId());
        if (subServiceEntity.isEmpty()) {
            throw new EntityNotFoundException("SubService Entity with id %d not found".formatted(entity.getSubService().getId()));
        }
        entity.setSubService(subServiceEntity.get());

        ApplicationEntity saved = repository.save(entity);
        return (ApplicationDto) mapper.mapToDto(saved);
    }

    @Override
    @Transactional
    public ApplicationDto update(ApplicationDto dto) {
        Optional<ApplicationEntity> og = repository.findById(dto.getId());
        if (og.isEmpty()) {
            throw new EntityNotFoundException("Application Entity with id %d and reference number %s not found".formatted(dto.getId(), dto.getReferenceNumber()));
        }
        ApplicationEntity nu = (ApplicationEntity) mapper.mapToEntity(dto);
        ApplicationEntity merged = (ApplicationEntity) mapper.merge(nu, og.get());
        ApplicationEntity saved = repository.save(merged);
        return (ApplicationDto) mapper.mapToDto(saved);
    }

    @Override
    @Transactional
    public ApplicationDto delete(ApplicationDto dto) {
        Optional<ApplicationEntity> og = repository.findById(dto.getId());
        if (og.isEmpty()) {
            throw new EntityNotFoundException("Application Entity with id %d and reference number %s not found".formatted(dto.getId(), dto.getReferenceNumber()));
        }

        // detach relations
        if (og.get().getUser() != null) {
            og.get().setUser(null);
        }

        if (og.get().getService() != null) {
            og.get().setService(null);
        }

        if (og.get().getSubService() != null) {
            og.get().setSubService(null);
        }

        if (og.get().getLab() != null) {
            og.get().setLab(null);
        }

        if (!og.get().getDevices().isEmpty()) {
            for (DeviceEntity device : og.get().getDevices()) {
                device.setApplication(null);
            }
            og.get().setDevices(new HashSet<>());
        }

        if (!og.get().getDocuments().isEmpty()) {
            for (DocumentEntity document : og.get().getDocuments()) {
                document.setApplication(null);
            }
            og.get().setDocuments(new HashSet<>());
        }

        if (og.get().getQuotation() != null) {
            og.get().getQuotation().setApplication(null);
            og.get().setQuotation(null);
        }

        if (og.get().getStatus() != null) {
            og.get().getStatus().getApplications().remove(og.get());
            og.get().setStatus(null);
        }

        repository.delete(og.get());
        return dto;
    }

    private String generateReferenceNumber(ApplicationDto dto) throws Exception {
        if (dto.getService() == null) {
            throw new Exception("Service not implemented");
        }
        if (dto.getSubService() == null) {
            throw new Exception("SubService not implemented");
        }
        if (dto.getSubService().getForm() == null) {
            throw new Exception("Form not implemented");
        }

        String prefix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        long precedingId = 1L;
        if (repository.findPrecedingId().isPresent()) {
            precedingId = repository.findPrecedingId().get() + 1;
        }

        return "%s%02d%02d".formatted(prefix, dto.getSubService().getForm().getId(), precedingId);
    }
}
