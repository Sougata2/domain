package com.sougata.domain.domain.workFlowAction.service.impl;

import com.sougata.domain.domain.status.dto.StatusDto;
import com.sougata.domain.domain.status.entity.StatusEntity;
import com.sougata.domain.domain.status.repository.StatusRepository;
import com.sougata.domain.domain.workFlowAction.dto.WorkFlowActionDto;
import com.sougata.domain.domain.workFlowAction.entity.WorkFlowActionEntity;
import com.sougata.domain.domain.workFlowAction.enums.WorkFlowMovement;
import com.sougata.domain.domain.workFlowAction.repository.WorkFlowActionRepository;
import com.sougata.domain.domain.workFlowAction.service.WorkFlowActionService;
import com.sougata.domain.domain.workFlowGroup.entity.WorkFlowGroupEntity;
import com.sougata.domain.mapper.RelationalMapper;
import com.sougata.domain.role.entity.RoleEntity;
import com.sougata.domain.role.repository.RoleRepository;
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
public class WorkFlowActionServiceImpl implements WorkFlowActionService {
    private final WorkFlowActionRepository repository;
    private final StatusRepository statusRepository;
    private final RoleRepository roleRepository;
    private final RelationalMapper mapper;

    @Override
    public List<WorkFlowActionDto> findByStatusId(Long statusId) {
        try {
            Optional<StatusEntity> status = statusRepository.findById(statusId);
            if (status.isEmpty()) {
                throw new EntityNotFoundException("status entity with id %d not found".formatted(statusId));
            }
            List<WorkFlowActionEntity> entities = repository.findByStatusId(status.get().getId());
            return entities.stream().map(e -> (WorkFlowActionDto) mapper.mapToDto(e)).toList();
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<WorkFlowActionDto> findAll() {
        try {
            List<WorkFlowActionEntity> entities = repository.findAll();
            return entities.stream().map(e -> (WorkFlowActionDto) mapper.mapToDto(e)).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<WorkFlowActionDto> search(Map<String, String> filter, Pageable pageable) {
        try {
            Specification<WorkFlowActionEntity> specification = (root, query, cb) -> {
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

                    predicates.add(cb.like(cb.lower(path.as(String.class)), "%" + value.toLowerCase() + "%"));
                }

                return cb.and(predicates.toArray(new Predicate[0]));
            };

            Page<WorkFlowActionEntity> page = repository.findAll(specification, pageable);

            List<WorkFlowActionDto> dtos = page.getContent()
                    .stream()
                    .map(e -> (WorkFlowActionDto) mapper.mapToDto(e))
                    .toList();

            return new PageImpl<>(dtos, pageable, page.getTotalElements());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<StatusDto> findTargetStatusByCurrentStatus(Long statusId) {
        try {
            Optional<StatusEntity> status = statusRepository.findById(statusId);
            if (status.isEmpty()) {
                throw new EntityNotFoundException("Status entity with id %d not found".formatted(statusId));
            }
            List<StatusEntity> entities = repository.findTargetStatusByCurrentStatus(status.get().getId());
            return entities.stream().map(e -> (StatusDto) mapper.mapToDto(e)).toList();
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WorkFlowActionDto findById(Long id) {
        try {
            Optional<WorkFlowActionEntity> entity = repository.findById(id);
            if (entity.isEmpty()) {
                throw new EntityNotFoundException("Work Flow Action entity with id %d not found".formatted(id));
            }
            return (WorkFlowActionDto) mapper.mapToDto(entity.get());
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public WorkFlowActionDto create(WorkFlowActionDto dto) {
        try {
            Optional<StatusEntity> status = statusRepository.findById(dto.getStatus().getId());
            if (status.isEmpty()) {
                throw new EntityNotFoundException("Status entity with id %d not found".formatted(dto.getStatus().getId()));
            }

            Optional<RoleEntity> targetRole = roleRepository.findById(dto.getTargetRole().getId());
            if (targetRole.isEmpty()) {
                throw new EntityNotFoundException("Role with id %d not found".formatted(dto.getTargetRole().getId()));
            }

            Optional<StatusEntity> targetStatus = statusRepository.findById(dto.getTargetStatus().getId());
            if (targetStatus.isEmpty()) {
                throw new EntityNotFoundException("Status with id %d not found".formatted(dto.getTargetStatus().getId()));
            }

            if (Arrays.stream(WorkFlowMovement.values()).noneMatch(m -> dto.getMovement() == m)) {
                throw new EntityNotFoundException("Movement %s not found".formatted(dto.getMovement()));
            }

            WorkFlowActionEntity entity = (WorkFlowActionEntity) mapper.mapToEntity(dto);
            entity.setStatus(status.get());
            entity.setTargetRole(targetRole.get());
            entity.setTargetStatus(targetStatus.get());
            entity.setMovement(dto.getMovement());

            WorkFlowActionEntity newEntity = repository.save(entity);
            return (WorkFlowActionDto) mapper.mapToDto(newEntity);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public WorkFlowActionDto update(WorkFlowActionDto dto) {
        try {

            Optional<WorkFlowActionEntity> og = repository.findById(dto.getId());
            if (og.isEmpty()) {
                throw new EntityNotFoundException("Work Flow Action with id %d not found".formatted(dto.getId()));
            }

            if (dto.getStatus() != null) {
                Optional<StatusEntity> status = statusRepository.findById(dto.getStatus().getId());
                if (status.isEmpty()) {
                    throw new EntityNotFoundException("Status entity with id %d not found".formatted(dto.getStatus().getId()));
                }
            }

            if (dto.getTargetRole() != null) {
                Optional<RoleEntity> targetRole = roleRepository.findById(dto.getTargetRole().getId());
                if (targetRole.isEmpty()) {
                    throw new EntityNotFoundException("Role with id %d not found".formatted(dto.getTargetRole().getId()));
                }
            }

            if (dto.getTargetStatus() != null) {
                Optional<StatusEntity> targetStatus = statusRepository.findById(dto.getTargetStatus().getId());
                if (targetStatus.isEmpty()) {
                    throw new EntityNotFoundException("Status with id %d not found".formatted(dto.getTargetStatus().getId()));
                }
            }

            if (dto.getMovement() != null) {
                if (Arrays.stream(WorkFlowMovement.values()).noneMatch(m -> dto.getMovement() == m)) {
                    throw new EntityNotFoundException("Movement %s not found".formatted(dto.getMovement()));
                }
            }

            WorkFlowActionEntity nu = (WorkFlowActionEntity) mapper.mapToEntity(dto);
            WorkFlowActionEntity merged = (WorkFlowActionEntity) mapper.merge(nu, og.get());
            WorkFlowActionEntity newEntity = repository.save(merged);
            return (WorkFlowActionDto) mapper.mapToDto(newEntity);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public WorkFlowActionDto delete(WorkFlowActionDto dto) {
        try {
            Optional<WorkFlowActionEntity> og = repository.findById(dto.getId());
            if (og.isEmpty()) {
                throw new EntityNotFoundException("Work Flow Action with id %d not found".formatted(dto.getId()));
            }
            // detach relations
            if (og.get().getStatus() != null) {
                og.get().getStatus().getActions().remove(og.get());
                og.get().setStatus(null);
            }

            if (og.get().getTargetRole() != null) {
                og.get().getTargetRole().getActions().remove(og.get());
                og.get().setTargetRole(null);
            }

            if (og.get().getTargetStatus() != null) {
                og.get().getTargetStatus().getTargetStatusActions().remove(og.get());
                og.get().setTargetStatus(null);
            }

            if (!og.get().getGroups().isEmpty()) {
                for (WorkFlowGroupEntity group : og.get().getGroups()) {
                    group.getActions().remove(og.get());
                }
                og.get().setGroups(new HashSet<>());
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
