package com.sougata.domain.domain.application.service.impl;

import com.sougata.domain.domain.application.dto.ApplicationDto;
import com.sougata.domain.domain.application.dto.ApplicationProcessDto;
import com.sougata.domain.domain.application.entity.ApplicationEntity;
import com.sougata.domain.domain.application.repository.ApplicationRepository;
import com.sougata.domain.domain.application.service.ApplicationService;
import com.sougata.domain.domain.devices.entity.DeviceEntity;
import com.sougata.domain.domain.document.entity.DocumentEntity;
import com.sougata.domain.domain.services.entity.ServiceEntity;
import com.sougata.domain.domain.services.repository.ServiceRepository;
import com.sougata.domain.domain.status.dto.StatusDto;
import com.sougata.domain.domain.status.entity.StatusEntity;
import com.sougata.domain.domain.status.repository.StatusRepository;
import com.sougata.domain.domain.subService.entity.SubServiceEntity;
import com.sougata.domain.domain.subService.repository.SubServiceRepository;
import com.sougata.domain.domain.workFlowAction.entity.WorkFlowActionEntity;
import com.sougata.domain.domain.workFlowAction.repository.WorkFlowActionRepository;
import com.sougata.domain.domain.workflowHistory.dto.WorkFlowHistoryDto;
import com.sougata.domain.domain.workflowHistory.entity.WorkFlowHistoryEntity;
import com.sougata.domain.mapper.RelationalMapper;
import com.sougata.domain.role.dto.RoleDto;
import com.sougata.domain.user.entity.UserEntity;
import com.sougata.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository repository;
    private final RelationalMapper mapper;
    private final ServiceRepository serviceRepository;
    private final SubServiceRepository subServiceRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final WorkFlowActionRepository workFlowActionRepository;

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
    public Page<ApplicationDto> findByStatusNameAndApplicantId(String statusName, Long applicantId, Pageable pageable) {
        Page<ApplicationEntity> entityPage = repository.findByStatusNameAndApplicantId(statusName, applicantId, pageable);
        List<ApplicationDto> dtoList = entityPage.stream().map(e -> (ApplicationDto) mapper.mapToDto(e)).toList();
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    @Override
    public Page<ApplicationDto> search(Map<String, String> filter, Pageable pageable) {
        try {
            Specification<ApplicationEntity> specification = (root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();

                for (Map.Entry<String, String> entry : filter.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    if (value == null || value.isEmpty()) continue;

                    Path<String> path;
                    if (key.contains(".")) {
                        String[] parts = key.split("\\.");
                        path = root.join(parts[0]).get(parts[1]);
                    } else {
                        path = root.get(key);
                    }

                    if (Number.class.isAssignableFrom(path.getJavaType())) {
                        // for numeric fields like assignee.id
                        predicates.add(cb.equal(path, Long.valueOf(value)));
                    } else {
                        // for string fields
                        predicates.add(cb.like(cb.lower(path.as(String.class)), "%" + value.toLowerCase() + "%"));
                    }
                }
                return cb.and(predicates.toArray(new Predicate[0]));
            };

            Page<ApplicationEntity> page = repository.findAll(specification, pageable);
            List<ApplicationDto> dtos = page.getContent().stream().map(e -> (ApplicationDto) mapper.mapToDto(e)).toList();
            return new PageImpl<>(dtos, pageable, page.getTotalElements());
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<ApplicationDto> findSubmittedApplicationsByApplicantId(Long applicantId, Pageable pageable) {
        try {
            Optional<UserEntity> user = userRepository.findById(applicantId);
            if (user.isEmpty()) {
                throw new EntityNotFoundException("Applicant with id %d not found".formatted(applicantId));
            }
            Page<ApplicationEntity> page = repository.findSubmittedApplicationByApplicantId(applicantId, pageable);
            List<ApplicationDto> dtos = page.stream().map(e -> (ApplicationDto) mapper.mapToDto(e)).toList();
            return new PageImpl<>(dtos, pageable, page.getTotalElements());
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

        // set the reference of applicant/assignee
        Optional<UserEntity> userEntity = userRepository.findById(dto.getApplicant().getId());
        if (userEntity.isEmpty()) {
            throw new EntityNotFoundException("User with id %d not found".formatted(dto.getApplicant().getId()));
        }
        entity.setApplicant(userEntity.get());
        entity.setAssignee(userEntity.get());


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
        if (og.get().getApplicant() != null) {
            og.get().setApplicant(null);
        }
        if (og.get().getAssignee() != null) {
            og.get().setAssignee(null);
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

        if (!og.get().getWorkFlowHistory().isEmpty()) {
            for (WorkFlowHistoryEntity workflow : og.get().getWorkFlowHistory()) {
                workflow.setApplication(null);
            }
            og.get().setWorkFlowHistory(new HashSet<>());
        }

        repository.delete(og.get());
        return dto;
    }

    @Override
    @Transactional
    public WorkFlowHistoryDto doNext(ApplicationProcessDto dto) {
        try {
            Optional<WorkFlowActionEntity> workFlowAction = workFlowActionRepository.findById(dto.getWorkFlowAction().getId());
            if (workFlowAction.isEmpty()) {
                throw new EntityNotFoundException("WorkFlowAction with id %d not found".formatted(dto.getWorkFlowAction().getId()));
            }

            StatusDto targetStatus = (StatusDto) mapper.mapToDto(workFlowAction.get().getTargetStatus());
            RoleDto targetRole = (RoleDto) mapper.mapToDto(workFlowAction.get().getTargetRole());

            WorkFlowHistoryDto historyDto = new WorkFlowHistoryDto();
            historyDto.setApplication(dto.getApplication());
            historyDto.setAssigner(dto.getAssigner());
            historyDto.setAssignee(dto.getAssignee());
            historyDto.setStatus(targetStatus);
            historyDto.setMovement(workFlowAction.get().getMovement());
            historyDto.setTargetRole(targetRole);
            historyDto.setComments(dto.getComments());
            return historyDto;
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
