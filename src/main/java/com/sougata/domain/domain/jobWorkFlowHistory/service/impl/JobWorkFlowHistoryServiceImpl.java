package com.sougata.domain.domain.jobWorkFlowHistory.service.impl;

import com.sougata.domain.domain.job.entity.JobEntity;
import com.sougata.domain.domain.job.repository.JobRepository;
import com.sougata.domain.domain.jobWorkFlowHistory.dto.JobWorkFlowHistoryDto;
import com.sougata.domain.domain.jobWorkFlowHistory.entity.JobWorkFlowHistoryEntity;
import com.sougata.domain.domain.jobWorkFlowHistory.repository.JobWorkFlowHistoryRepository;
import com.sougata.domain.domain.jobWorkFlowHistory.service.JobWorkFlowHistoryService;
import com.sougata.domain.domain.status.entity.StatusEntity;
import com.sougata.domain.domain.status.repository.StatusRepository;
import com.sougata.domain.file.entity.FileEntity;
import com.sougata.domain.file.repository.FileRepository;
import com.sougata.domain.mapper.RelationalMapper;
import com.sougata.domain.user.dto.UserDto;
import com.sougata.domain.user.entity.UserEntity;
import com.sougata.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobWorkFlowHistoryServiceImpl implements JobWorkFlowHistoryService {
    private final JobWorkFlowHistoryRepository repository;
    private final StatusRepository statusRepository;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final RelationalMapper mapper;

    @Override
    public List<JobWorkFlowHistoryDto> findByJobId(Long jobId) {
        try {
            Optional<JobEntity> job = jobRepository.findById(jobId);
            if (job.isEmpty()) {
                throw new EntityNotFoundException("Job Entity with ID: %d is not found".formatted(jobId));
            }
            List<JobWorkFlowHistoryEntity> history = repository.findByJobId(jobId);
            return history.stream().map(e -> (JobWorkFlowHistoryDto) mapper.mapToDto(e)).toList();
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JobWorkFlowHistoryDto findById(Long id) {
        try {
            Optional<JobWorkFlowHistoryEntity> job = repository.findById(id);
            if (job.isEmpty()) {
                throw new EntityNotFoundException("Job work flow history with ID: %d is not found".formatted(id));
            }
            return (JobWorkFlowHistoryDto) mapper.mapToDto(job.get());
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getRegressiveUser(Long jobId, Long targetRoleId) {
        try {
            List<JobWorkFlowHistoryEntity> history = repository.findByJobId(jobId);
            for (JobWorkFlowHistoryEntity historyEntity : history.reversed()) {
                if (historyEntity.getAssigner().getDefaultRole().getId().equals(targetRoleId)) {
                    Optional<UserEntity> formerAssigner = userRepository.findById(historyEntity.getAssigner().getId());
                    if (formerAssigner.isEmpty()) {
                        throw new EntityNotFoundException("Assigner(User) [history table] with id %d is not found".formatted(historyEntity.getAssigner().getId()));
                    }
                    return List.of((UserDto) mapper.mapToDto(formerAssigner.get()));
                }
            }
            throw new RuntimeException("No such former assigner with the target role id %d".formatted(targetRoleId));
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public JobWorkFlowHistoryDto create(JobWorkFlowHistoryDto dto) {
        try {
            Optional<JobEntity> job = jobRepository.findById(dto.getJob().getId());
            if (job.isEmpty()) {
                throw new EntityNotFoundException("Job entity with ID :  %d is not found".formatted(dto.getJob().getId()));
            }
            Optional<StatusEntity> status = statusRepository.findById(dto.getStatus().getId());
            if (status.isEmpty()) {
                throw new EntityNotFoundException("Status entity with ID :  %d is not found".formatted(dto.getStatus().getId()));
            }

            Optional<UserEntity> assigner = userRepository.findById(dto.getAssigner().getId());
            if (assigner.isEmpty()) {
                throw new EntityNotFoundException("User entity with ID : %d is not found".formatted(dto.getAssigner().getId()));
            }

            JobWorkFlowHistoryEntity entity = (JobWorkFlowHistoryEntity) mapper.mapToEntity(dto);
            entity.setJob(job.get());
            entity.setAssigner(assigner.get());
            entity.setStatus(status.get());

            if (dto.getFile() != null) {
                Optional<FileEntity> file = fileRepository.findById(dto.getFile().getId());
                if (file.isEmpty()) {
                    throw new EntityNotFoundException("File Entity with ID : %d is not found".formatted(dto.getFile().getId()));
                }
                entity.setFile(file.get());
            }

            switch (dto.getMovement()) {
                case PROGRESSIVE -> {
                    Optional<UserEntity> assignee = userRepository.findById(dto.getAssignee().getId());
                    if (assignee.isEmpty()) {
                        throw new EntityNotFoundException("Assignee(user) [selected] with ID : %d is not found".formatted(dto.getAssignee().getId()));
                    }
                    entity.setAssignee(assignee.get());
                }
                case REGRESSIVE -> {
                    List<JobWorkFlowHistoryEntity> history = repository.findByJobId(dto.getJob().getId());
                    for (JobWorkFlowHistoryEntity historyEntity : history.reversed()) {
                        if (
                                historyEntity.getAssigner().getDefaultRole().getId()
                                        .equals(dto.getAssigner().getDefaultRole().getId())
                        ) {
                            Optional<UserEntity> formerAssigner = userRepository.findById(historyEntity.getAssigner().getId());
                            if (formerAssigner.isEmpty()) {
                                throw new EntityNotFoundException("Assigner (user) [history table] with ID : %d is not found".formatted(dto.getAssigner().getId()));
                            }
                            entity.setAssignee(formerAssigner.get());
                            break;
                        }
                    }
                }
                case PROGRESSIVE_ONE -> {
                    List<UserEntity> assignees = userRepository.findByDefaultRoleIdAndLabId(dto.getTargetRole().getId(), job.get().getLab().getId());
                    if (assignees.isEmpty()) {
                        throw new EntityNotFoundException("UserEntity[assignee] with default role: %d and labId %d does not exist".formatted(dto.getTargetRole().getId(), job.get().getLab().getId()));
                    } else if (assignees.size() > 1) {
                        throw new NonUniqueResultException("Too Many Assignees with default role %d and lab %d".formatted(dto.getTargetRole().getId(), job.get().getLab().getId()));
                    }
                    entity.setAssignee(assignees.getFirst());
                }
            }

            job.get().setAssignee(entity.getAssignee());
            job.get().setStatus(entity.getStatus());
            jobRepository.save(job.get());

            JobWorkFlowHistoryEntity saved = repository.save(entity);
            return (JobWorkFlowHistoryDto) mapper.mapToDto(saved);
        } catch (EntityNotFoundException | NonUniqueResultException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public JobWorkFlowHistoryDto update(JobWorkFlowHistoryDto dto) {
        try {
            Optional<JobWorkFlowHistoryEntity> og = repository.findById(dto.getJob().getId());
            if (og.isEmpty()) {
                throw new EntityNotFoundException("JobEntity with ID : %d is not found".formatted(dto.getJob().getId()));
            }

            Optional<JobEntity> job = jobRepository.findById(dto.getJob().getId());
            if (job.isEmpty()) {
                throw new EntityNotFoundException("Job entity with ID :  %d is not found".formatted(dto.getJob().getId()));
            }
            Optional<StatusEntity> status = statusRepository.findById(dto.getStatus().getId());
            if (status.isEmpty()) {
                throw new EntityNotFoundException("Status entity with ID :  %d is not found".formatted(dto.getStatus().getId()));
            }

            Optional<UserEntity> assigner = userRepository.findById(dto.getAssigner().getId());
            if (assigner.isEmpty()) {
                throw new EntityNotFoundException("User entity with ID : %d is not found".formatted(dto.getAssigner().getId()));
            }

            if (dto.getFile() != null) {
                Optional<FileEntity> file = fileRepository.findById(dto.getFile().getId());
                if (file.isEmpty()) {
                    throw new EntityNotFoundException("File Entity with ID : %d is not found".formatted(dto.getFile().getId()));
                }
            }

            JobWorkFlowHistoryEntity nu = (JobWorkFlowHistoryEntity) mapper.mapToEntity(dto);
            JobWorkFlowHistoryEntity merged = (JobWorkFlowHistoryEntity) mapper.merge(nu, og.get());

            JobWorkFlowHistoryEntity saved = repository.save(merged);
            return (JobWorkFlowHistoryDto) mapper.mapToDto(saved);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public JobWorkFlowHistoryDto delete(JobWorkFlowHistoryDto dto) {
        try {
            Optional<JobWorkFlowHistoryEntity> og = repository.findById(dto.getJob().getId());
            if (og.isEmpty()) {
                throw new EntityNotFoundException("JobEntity with ID : %d is not found".formatted(dto.getJob().getId()));
            }

            // detach relations
            if (og.get().getJob() != null) {
                og.get().getJob().getWorkFlowHistory().remove(og.get());
                og.get().setJob(null);
            }

            if (og.get().getStatus() != null) {
                og.get().getStatus().getJobWorkFlowHistory().remove(og.get());
                og.get().setStatus(null);
            }

            if (og.get().getAssigner() != null) {
                og.get().getAssigner().getJobWorkFlowHistoryForAssigner().remove(og.get());
                og.get().setAssigner(null);
            }

            if (og.get().getAssignee() != null) {
                og.get().getAssignee().getJobWorkFlowHistoryForAssignee().remove(og.get());
                og.get().setAssignee(null);
            }

            repository.delete(og.get());
            return dto;
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
