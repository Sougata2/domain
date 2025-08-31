package com.sougata.domain.domain.workFlowGroup.service.impl;

import com.sougata.domain.domain.subService.entity.SubServiceEntity;
import com.sougata.domain.domain.workFlowAction.entity.WorkFlowActionEntity;
import com.sougata.domain.domain.workFlowGroup.dto.WorkFlowGroupDto;
import com.sougata.domain.domain.workFlowGroup.entity.WorkFlowGroupEntity;
import com.sougata.domain.domain.workFlowGroup.repository.WorkFlowGroupRepository;
import com.sougata.domain.domain.workFlowGroup.service.WorkFlowGroupService;
import com.sougata.domain.mapper.RelationalMapper;
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
public class WorkFlowGroupServiceImpl implements WorkFlowGroupService {
    private final WorkFlowGroupRepository repository;
    private final RelationalMapper mapper;

    @Override
    public Page<WorkFlowGroupDto> search(Map<String, String> filter, Pageable pageable) {
        try {
            Specification<WorkFlowGroupEntity> specification = (root, query, cb) -> {
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

                    if (Number.class.isAssignableFrom(path.getClass())) {
                        predicates.add(cb.equal(path, Long.valueOf(value)));
                    } else {
                        predicates.add(cb.like(cb.lower(path.as(String.class)), "%" + value.toLowerCase() + "%"));
                    }
                }
                return cb.and(predicates.toArray(new Predicate[0]));
            };
            Page<WorkFlowGroupEntity> page = repository.findAll(specification, pageable);
            List<WorkFlowGroupDto> dtos = page.stream().map(e -> (WorkFlowGroupDto) mapper.mapToDto(e)).toList();
            return new PageImpl<>(dtos, pageable, page.getTotalElements());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<WorkFlowGroupDto> findAll() {
        try {
            List<WorkFlowGroupEntity> entities = repository.findAll();
            return entities.stream().map(e -> (WorkFlowGroupDto) mapper.mapToDto(e)).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WorkFlowGroupDto findById(Long id) {
        try {
            Optional<WorkFlowGroupEntity> entity = repository.findById(id);
            if (entity.isEmpty()) {
                throw new EntityNotFoundException("WorkFlowGroup with Id %d, not found".formatted(id));
            }
            return (WorkFlowGroupDto) mapper.mapToDto(entity.get());
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public WorkFlowGroupDto create(WorkFlowGroupDto dto) {
        try {
            WorkFlowGroupEntity entity = (WorkFlowGroupEntity) mapper.mapToEntity(dto);
            WorkFlowGroupEntity saved = repository.save(entity);
            return (WorkFlowGroupDto) mapper.mapToDto(saved);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public WorkFlowGroupDto update(WorkFlowGroupDto dto) {
        try {
            Optional<WorkFlowGroupEntity> og = repository.findById(dto.getId());
            if (og.isEmpty()) {
                throw new EntityNotFoundException("WorkFlowGroup with Id %d, not found".formatted(dto.getId()));
            }
            WorkFlowGroupEntity nu = (WorkFlowGroupEntity) mapper.mapToEntity(dto);
            WorkFlowGroupEntity merged = (WorkFlowGroupEntity) mapper.merge(nu, og.get());
            WorkFlowGroupEntity saved = repository.save(merged);
            return (WorkFlowGroupDto) mapper.mapToDto(saved);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public WorkFlowGroupDto delete(WorkFlowGroupDto dto) {
        try {
            Optional<WorkFlowGroupEntity> og = repository.findById(dto.getId());
            if (og.isEmpty()) {
                throw new EntityNotFoundException("WorkFlowGroup with Id %d, not found".formatted(dto.getId()));
            }

            //detach relation
            if (!og.get().getSubServices().isEmpty()) {
                for (SubServiceEntity subService : og.get().getSubServices()) {
                    subService.setWorkFlowGroup(null);
                }
                og.get().setSubServices(new HashSet<>());
            }

            if (!og.get().getActions().isEmpty()) {
                for (WorkFlowActionEntity action : og.get().getActions()) {
                    action.getGroups().remove(og.get());
                }
                og.get().setActions(new HashSet<>());
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
