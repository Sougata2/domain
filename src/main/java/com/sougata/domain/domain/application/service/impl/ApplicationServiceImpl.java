package com.sougata.domain.domain.application.service.impl;

import com.sougata.domain.domain.application.dto.ApplicationDto;
import com.sougata.domain.domain.application.entity.ApplicationEntity;
import com.sougata.domain.domain.application.repository.ApplicationRepository;
import com.sougata.domain.domain.application.service.ApplicationService;
import com.sougata.domain.domain.devices.entity.DeviceEntity;
import com.sougata.domain.domain.document.entity.DocumentEntity;
import com.sougata.domain.domain.services.entity.ServiceEntity;
import com.sougata.domain.domain.services.repository.ServiceRepository;
import com.sougata.domain.domain.status.entity.StatusEntity;
import com.sougata.domain.domain.status.repository.StatusRepository;
import com.sougata.domain.mapper.RelationalMapper;
import com.sougata.domain.subService.entity.SubServiceEntity;
import com.sougata.domain.subService.repository.SubServiceRepository;
import com.sougata.domain.user.entity.UserEntity;
import com.sougata.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;

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
    public Page<ApplicationDto> findByStatusNameAndUserId(String statusName, Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ApplicationEntity> entityPage = repository.findByStatusNameAndUserId(statusName, userId, pageable);
        List<ApplicationDto> dtoList = entityPage.stream().map(e -> (ApplicationDto) mapper.mapToDto(e)).toList();
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    @Override
    @Transactional
    public ApplicationDto create(ApplicationDto dto) throws Exception {
        ApplicationEntity entity = new ApplicationEntity();

        // set the reference to service
        Optional<ServiceEntity> serviceEntity = serviceRepository.findById(dto.getService().getId());
        if (serviceEntity.isEmpty()) {
            throw new EntityNotFoundException("Service Entity with id %d not found".formatted(dto.getService().getId()));
        }
        entity.setService(serviceEntity.get());

        // set the reference to subService
        Optional<SubServiceEntity> subServiceEntity = subServiceRepository.findById(dto.getSubService().getId());
        if (subServiceEntity.isEmpty()) {
            throw new EntityNotFoundException("SubService Entity with id %d not found".formatted(dto.getSubService().getId()));
        }
        entity.setSubService(subServiceEntity.get());

        // set the reference of user
        Optional<UserEntity> userEntity = userRepository.findById(dto.getUser().getId());
        if (userEntity.isEmpty()) {
            throw new EntityNotFoundException("User with id %d not found".formatted(dto.getUser().getId()));
        }
        entity.setUser(userEntity.get());

        // set the reference of status
        Optional<StatusEntity> statusEntity = statusRepository.findById(dto.getStatus().getId());
        if (statusEntity.isEmpty()) {
            throw new EntityNotFoundException("Status Entity with id %d not found".formatted(dto.getStatus().getId()));
        }
        entity.setStatus(statusEntity.get());

        // generating reference number
        String referenceNumber = generateReferenceNumber(entity);
        entity.setReferenceNumber(referenceNumber);
        // generating reference number

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

    private String generateReferenceNumber(ApplicationEntity entity) throws Exception {
        if (entity.getService() == null) {
            throw new Exception("Service not implemented");
        }
        if (entity.getSubService() == null) {
            throw new Exception("SubService not implemented");
        }
        if (entity.getSubService().getForm() == null) {
            throw new Exception("Form not implemented");
        }

        String prefix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        long precedingId = 1L;
        if (repository.findPrecedingId().isPresent()) {
            precedingId = repository.findPrecedingId().get() + 1;
        }

        return "%s%02d%02d".formatted(prefix, entity.getSubService().getForm().getId(), precedingId);
    }
}
