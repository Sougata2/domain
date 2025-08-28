package com.sougata.domain.domain.workflowHistory.service.impl;

import com.sougata.domain.domain.application.entity.ApplicationEntity;
import com.sougata.domain.domain.application.repository.ApplicationRepository;
import com.sougata.domain.domain.status.entity.StatusEntity;
import com.sougata.domain.domain.status.repository.StatusRepository;
import com.sougata.domain.domain.workflowHistory.dto.WorkFlowHistoryDto;
import com.sougata.domain.domain.workflowHistory.entity.WorkFlowHistoryEntity;
import com.sougata.domain.domain.workflowHistory.repository.WorkFlowHistoryRepository;
import com.sougata.domain.domain.workflowHistory.service.WorkFlowHistoryService;
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
public class WorkFlowHistoryServiceImpl implements WorkFlowHistoryService {
    private final ApplicationRepository applicationRepository;
    private final WorkFlowHistoryRepository repository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;
    private final RelationalMapper mapper;

    @Override
    public List<WorkFlowHistoryDto> findByReferenceNumber(String referenceNumber) {
        try {
            Optional<ApplicationEntity> application = applicationRepository.findByReferenceNumber(referenceNumber);
            if (application.isEmpty()) {
                throw new EntityNotFoundException("Application with reference number %s is not found".formatted(referenceNumber));
            }
            List<WorkFlowHistoryEntity> entities = repository.findByReferenceNumber(referenceNumber);
            return entities.stream().map(e -> (WorkFlowHistoryDto) mapper.mapToDto(e)).toList();
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WorkFlowHistoryDto findById(Long id) {
        try {
            Optional<WorkFlowHistoryEntity> entity = repository.findById(id);
            if (entity.isEmpty()) {
                throw new EntityNotFoundException("WorkFlowHistory with id %s is not found".formatted(id));
            }
            return (WorkFlowHistoryDto) mapper.mapToDto(entity.get());
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserDto> getRegressiveUser(String referenceNumber, Long targetRoleId) {
        try {
            List<WorkFlowHistoryEntity> history = repository.findByReferenceNumber(referenceNumber);
            for (WorkFlowHistoryEntity historyEntity : history.reversed()) {
                if (
                        historyEntity.getAssigner().getDefaultRole().getId()
                                .equals(targetRoleId)
                ) {
                    Optional<UserEntity> formerAssigner = userRepository.findById(historyEntity.getAssigner().getId());
                    if (formerAssigner.isEmpty()) {
                        throw new EntityNotFoundException("Assigner(User) [history table] with id %s is not found".formatted(historyEntity.getAssigner().getId()));
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
    public WorkFlowHistoryDto create(WorkFlowHistoryDto dto) {
        try {
            Optional<ApplicationEntity> application = applicationRepository.findByReferenceNumber(dto.getApplication().getReferenceNumber());
            if (application.isEmpty()) {
                throw new EntityNotFoundException("Application with reference number %s is not found".formatted(dto.getApplication().getReferenceNumber()));
            }

            Optional<StatusEntity> status = statusRepository.findById(dto.getStatus().getId());
            if (status.isEmpty()) {
                throw new EntityNotFoundException("Status with id %s is not found".formatted(dto.getStatus().getId()));
            }

            Optional<UserEntity> assigner = userRepository.findById(dto.getAssigner().getId());
            if (assigner.isEmpty()) {
                throw new EntityNotFoundException("Assigner(User) with id %s is not found".formatted(dto.getAssigner().getId()));
            }

            WorkFlowHistoryEntity entity = new WorkFlowHistoryEntity();
            entity.setApplication(application.get());
            entity.setAssigner(assigner.get());
            entity.setStatus(status.get());

            switch (dto.getMovement()) {
                case PROGRESSIVE -> {
                    // selected by user
                    Optional<UserEntity> assignee = userRepository.findById(dto.getAssignee().getId());
                    if (assignee.isEmpty()) {
                        throw new EntityNotFoundException("Assignee(User) [selected] with id %s is not found".formatted(dto.getAssignee().getId()));
                    }
                    entity.setAssignee(assignee.get());
                }
                case REGRESSIVE -> {
                    // picking the previous assigner
                    List<WorkFlowHistoryEntity> history = repository.findByReferenceNumber(dto.getApplication().getReferenceNumber());
                    for (WorkFlowHistoryEntity historyEntity : history.reversed()) {
                        if (
                                historyEntity.getAssigner().getDefaultRole().getId()
                                        .equals(dto.getTargetRole().getId())
                        ) {
                            Optional<UserEntity> formerAssigner = userRepository.findById(historyEntity.getAssigner().getId());
                            if (formerAssigner.isEmpty()) {
                                throw new EntityNotFoundException("Assigner(User) [history table] with id %s is not found".formatted(dto.getAssigner().getId()));
                            }
                            entity.setAssignee(formerAssigner.get());
                            break;
                        }
                    }
                }
                case PROGRESSIVE_ONE -> {
                    // for the role which is assigned to a single user EG CSC HEAD, DDO HEAD.
                    List<UserEntity> assignees = userRepository.findByDefaultRoleIdAndLabId(dto.getTargetRole().getId(), application.get().getLab().getId());
                    if (assignees.isEmpty()) {
                        throw new EntityNotFoundException("UserEntity[assignee] with default role: %d and labId %d does not exist".formatted(dto.getTargetRole().getId(), application.get().getLab().getId()));
                    } else if (assignees.size() > 1) {
                        throw new NonUniqueResultException("Too Many Assignees with default role %d and lab %d".formatted(dto.getTargetRole().getId(), application.get().getLab().getId()));
                    }
                    entity.setAssignee(assignees.getFirst());
                }
            }

            application.get().setAssignee(entity.getAssignee());
            application.get().setStatus(status.get());
            applicationRepository.save(application.get());

            WorkFlowHistoryEntity saved = repository.save(entity);
            return (WorkFlowHistoryDto) mapper.mapToDto(saved);
        } catch (NonUniqueResultException | EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public WorkFlowHistoryDto update(WorkFlowHistoryDto dto) {
        try {
            Optional<WorkFlowHistoryEntity> og = repository.findById(dto.getId());
            if (og.isEmpty()) {
                throw new EntityNotFoundException("WorkFlowHistory with id %s is not found".formatted(dto.getId()));
            }


            if (dto.getApplication() != null && dto.getApplication().getReferenceNumber() != null) {
                Optional<ApplicationEntity> application = applicationRepository.findByReferenceNumber(dto.getApplication().getReferenceNumber());
                if (application.isEmpty()) {
                    throw new EntityNotFoundException("Application with reference number %s is not found".formatted(dto.getApplication().getReferenceNumber()));
                }
            }

            if (dto.getStatus() != null && dto.getStatus().getId() != null) {
                Optional<StatusEntity> status = statusRepository.findById(dto.getStatus().getId());
                if (status.isEmpty()) {
                    throw new EntityNotFoundException("Status with id %s is not found".formatted(dto.getStatus().getId()));
                }
            }

            if (dto.getAssigner() != null && dto.getAssigner().getId() != null) {
                Optional<UserEntity> assigner = userRepository.findById(dto.getAssigner().getId());
                if (assigner.isEmpty()) {
                    throw new EntityNotFoundException("Assigner(User) with id %s is not found".formatted(dto.getAssigner().getId()));
                }
            }

            if (dto.getAssignee() != null && dto.getAssignee().getId() != null) {
                Optional<UserEntity> assignee = userRepository.findById(dto.getAssignee().getId());
                if (assignee.isEmpty()) {
                    throw new EntityNotFoundException("Assignee(User) with id %s is not found".formatted(dto.getAssignee().getId()));
                }
            }

            WorkFlowHistoryEntity nu = (WorkFlowHistoryEntity) mapper.mapToEntity(dto);
            WorkFlowHistoryEntity merge = (WorkFlowHistoryEntity) mapper.merge(nu, og.get());
            WorkFlowHistoryEntity saved = repository.save(merge);
            return (WorkFlowHistoryDto) mapper.mapToDto(saved);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public WorkFlowHistoryDto delete(WorkFlowHistoryDto dto) {
        try {
            Optional<WorkFlowHistoryEntity> og = repository.findById(dto.getId());
            if (og.isEmpty()) {
                throw new EntityNotFoundException("WorkFlowHistory with id %s is not found".formatted(dto.getId()));
            }

            // detach relations
            if (og.get().getApplication() != null) {
                for (WorkFlowHistoryEntity workFlowHistory : og.get().getApplication().getWorkFlowHistory()) {
                    if (workFlowHistory.getId().equals(og.get().getId())) {
                        workFlowHistory.setApplication(null);
                    }
                }
                og.get().setApplication(null);
            }

            if (og.get().getAssigner() != null) {
                for (WorkFlowHistoryEntity workFlowHistory : og.get().getAssigner().getWorkFlowHistoryForAssigner()) {
                    if (workFlowHistory.getId().equals(og.get().getAssigner().getId())) {
                        workFlowHistory.setAssigner(null);
                    }
                }
                og.get().setAssigner(null);
            }

            if (og.get().getAssignee() != null) {
                for (WorkFlowHistoryEntity workFlowHistory : og.get().getAssignee().getWorkFlowHistoryForAssignee()) {
                    if (workFlowHistory.getId().equals(og.get().getAssignee().getId())) {
                        workFlowHistory.setAssignee(null);
                    }
                }
                og.get().setAssignee(null);
            }

            if (og.get().getStatus() != null) {
                for (WorkFlowHistoryEntity workFlowHistory : og.get().getStatus().getWorkFlowHistory()) {
                    if (workFlowHistory.getId().equals(og.get().getStatus().getId())) {
                        workFlowHistory.setStatus(null);
                    }
                }
                og.get().setStatus(null);
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
