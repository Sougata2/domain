package com.sougata.domain.domain.job.service.impl;

import com.sougata.domain.domain.application.entity.ApplicationEntity;
import com.sougata.domain.domain.application.repository.ApplicationRepository;
import com.sougata.domain.domain.devices.entity.DeviceEntity;
import com.sougata.domain.domain.devices.repository.DeviceRepository;
import com.sougata.domain.domain.job.dto.JobDto;
import com.sougata.domain.domain.job.dto.JobProcessDto;
import com.sougata.domain.domain.job.entity.JobEntity;
import com.sougata.domain.domain.job.repository.JobRepository;
import com.sougata.domain.domain.job.service.JobService;
import com.sougata.domain.domain.jobWorkFlowHistory.dto.JobWorkFlowHistoryDto;
import com.sougata.domain.domain.jobWorkFlowHistory.entity.JobWorkFlowHistoryEntity;
import com.sougata.domain.domain.lab.entity.LabEntity;
import com.sougata.domain.domain.lab.repository.LabRepository;
import com.sougata.domain.domain.status.dto.StatusDto;
import com.sougata.domain.domain.status.entity.StatusEntity;
import com.sougata.domain.domain.status.repository.StatusRepository;
import com.sougata.domain.domain.workFlowAction.entity.WorkFlowActionEntity;
import com.sougata.domain.domain.workFlowAction.repository.WorkFlowActionRepository;
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

import java.util.*;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final RelationalMapper mapper;
    private final JobRepository repository;
    private final LabRepository labRepository;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final StatusRepository statusRepository;
    private final ApplicationRepository applicationRepository;
    private final WorkFlowActionRepository workFlowActionRepository;

    @Override
    public JobDto findByDeviceId(Long deviceId) {
        try {
            Optional<DeviceEntity> device = deviceRepository.findById(deviceId);
            if (device.isEmpty()) {
                throw new EntityNotFoundException("Device entity with ID : %d is not found".formatted(deviceId));
            }
            Optional<JobEntity> job = repository.findByDeviceId(deviceId);
            if (job.isEmpty()) {
                throw new EntityNotFoundException("Job for device ID : %d does not exists".formatted(deviceId));
            }
            return (JobDto) mapper.mapToDto(job.get());
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<JobDto> findByApplicationReferenceNumber(String referenceNumber) {
        try {
            Optional<ApplicationEntity> application = applicationRepository.findByReferenceNumber(referenceNumber);
            if (application.isEmpty()) {
                throw new EntityNotFoundException("Application with reference number : %s is not found".formatted(referenceNumber));
            }
            List<JobEntity> jobs = repository.findByApplicationReferenceNumber(referenceNumber);
            return jobs.stream().map(e -> (JobDto) mapper.mapToDto(e)).toList();
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobDto> search(Map<String, String> filters, Pageable pageable) {
        try {
            Specification<JobEntity> specification = (root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();

                for (Map.Entry<String, String> entry : filters.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    if (value == null || value.isEmpty()) continue;

                    Path<String> path;
                    if (key.contains(".")) {
                        String[] parts = key.split("\\.");
                        path = root.get(parts[0]).get(parts[1]);
                    } else {
                        path = root.get(key);
                    }

                    if (Number.class.isAssignableFrom(path.getJavaType())) {
                        predicates.add(cb.equal(path, Long.valueOf(value)));
                    } else {
                        predicates.add(cb.like(cb.lower(path.as(String.class)), "%" + value.toLowerCase() + "%"));
                    }
                }
                return cb.and(predicates.toArray(new Predicate[0]));
            };

            Page<JobEntity> page = repository.findAll(specification, pageable);
            List<JobDto> dtos = page.getContent().stream().map(e -> (JobDto) mapper.mapToDto(e)).toList();
            return new PageImpl<>(dtos, pageable, page.getTotalElements());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JobDto findById(Long id) {
        try {
            Optional<JobEntity> job = repository.findById(id);
            if (job.isEmpty()) {
                throw new EntityNotFoundException("Job for id : %d is not found".formatted(id));
            }
            return (JobDto) mapper.mapToDto(job.get());
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public JobDto create(JobDto dto) {
        try {
            Optional<DeviceEntity> device = deviceRepository.findById(dto.getDevice().getId());
            if (device.isEmpty()) {
                throw new EntityNotFoundException("Device entity with ID : %d is not found".formatted(dto.getDevice().getId()));
            }
            Optional<StatusEntity> status = statusRepository.findById(dto.getStatus().getId());
            if (status.isEmpty()) {
                throw new EntityNotFoundException("Status entity with ID :  %d is not found".formatted(dto.getStatus().getId()));
            }
            Optional<UserEntity> assignee = userRepository.findById(dto.getAssignee().getId());
            if (assignee.isEmpty()) {
                throw new EntityNotFoundException("User entity with ID : %d is not found".formatted(dto.getAssignee().getId()));
            }
            Optional<LabEntity> lab = labRepository.findById(dto.getLab().getId());
            if (lab.isEmpty()) {
                throw new EntityNotFoundException("Lab entity with ID : %d is not found".formatted(dto.getLab().getId()));
            }

            JobEntity job = new JobEntity();
            job.setDevice(device.get());
            job.setAssignee(assignee.get());
            job.setLab(lab.get());
            job.setStatus(status.get());

            JobEntity saved = repository.save(job);
            return (JobDto) mapper.mapToDto(saved);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public JobDto update(JobDto dto) {
        try {
            Optional<JobEntity> og = repository.findById(dto.getId());
            if (og.isEmpty()) {
                throw new EntityNotFoundException("Job for id : %d is not found".formatted(dto.getId()));
            }
            JobEntity nu = (JobEntity) mapper.mapToEntity(dto);
            JobEntity merged = (JobEntity) mapper.merge(nu, og.get());
            JobEntity saved = repository.save(merged);
            return (JobDto) mapper.mapToDto(saved);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public JobDto delete(JobDto dto) {
        try {
            Optional<JobEntity> entity = repository.findById(dto.getId());
            if (entity.isEmpty()) {
                throw new EntityNotFoundException("Job for id : %d is not found".formatted(dto.getId()));
            }

            // detach relations
            if (entity.get().getDevice() != null) {
                entity.get().getDevice().setJob(null);
                entity.get().setDevice(null);
            }

            if (entity.get().getStatus() != null) {
                entity.get().getStatus().getJobs().remove(entity.get());
                entity.get().setStatus(null);
            }

            if (entity.get().getAssignee() != null) {
                entity.get().getAssignee().getJobs().remove(entity.get());
                entity.get().setAssignee(null);
            }

            if (entity.get().getLab() != null) {
                entity.get().getLab().getJobs().remove(entity.get());
                entity.get().setLab(null);
            }

            if (!entity.get().getWorkFlowHistory().isEmpty()) {
                for (JobWorkFlowHistoryEntity history : entity.get().getWorkFlowHistory()) {
                    history.setJob(null);
                }
                entity.get().setWorkFlowHistory(new HashSet<>());
            }

            repository.delete(entity.get());
            return dto;
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public JobWorkFlowHistoryDto doNext(JobProcessDto dto) {
        try {
            Optional<WorkFlowActionEntity> workFlowAction = workFlowActionRepository.findById(dto.getWorkFlowAction().getId());
            if (workFlowAction.isEmpty()) {
                throw new EntityNotFoundException("JobWorkFlowAction Entity with ID : %d is not found".formatted(dto.getWorkFlowAction().getId()));
            }

            StatusDto targetStatus = (StatusDto) mapper.mapToDto(workFlowAction.get().getTargetStatus());
            RoleDto targetRole = (RoleDto) mapper.mapToDto(workFlowAction.get().getTargetRole());

            JobWorkFlowHistoryDto history = new JobWorkFlowHistoryDto();
            history.setJob(dto.getJob());
            history.setAssigner(dto.getAssigner());
            history.setAssignee(dto.getAssignee());
            history.setStatus(targetStatus);
            history.setMovement(workFlowAction.get().getMovement());
            history.setTargetRole(targetRole);
            history.setComments(dto.getComments());
            history.setFile(dto.getFile());
            return history;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
