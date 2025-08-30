package com.sougata.domain.subService.service.impl;

import com.sougata.domain.domain.services.entity.ServiceEntity;
import com.sougata.domain.mapper.RelationalMapper;
import com.sougata.domain.subService.dto.SubServiceDto;
import com.sougata.domain.subService.entity.SubServiceEntity;
import com.sougata.domain.subService.repository.SubServiceRepository;
import com.sougata.domain.subService.service.SubServiceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubServiceServiceImpl implements SubServiceService {
    private final SubServiceRepository repository;
    private final RelationalMapper mapper;

    @Override
    public List<SubServiceDto> findAllSubServices() {
        try {
            List<SubServiceEntity> list = repository.findAll();
            return list.stream().map(e -> (SubServiceDto) mapper.mapToDto(e)).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SubServiceDto findSubServiceById(Long id) {
        try {
            Optional<SubServiceEntity> entity = repository.findById(id);
            if (entity.isEmpty()) {
                throw new EntityNotFoundException("Sub Service with id %d not found".formatted(id));
            }
            return (SubServiceDto) mapper.mapToDto(entity.get());
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public SubServiceDto createSubService(SubServiceDto dto) {
        try {
            SubServiceEntity entity = (SubServiceEntity) mapper.mapToEntity(dto);
            SubServiceEntity saved = repository.save(entity);
            return (SubServiceDto) mapper.mapToDto(saved);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public SubServiceDto updateSubService(SubServiceDto dto) {
        try {
            Optional<SubServiceEntity> og = repository.findById(dto.getId());
            if (og.isEmpty()) {
                throw new EntityNotFoundException("Sub Service with id %d not found".formatted(dto.getId()));
            }
            SubServiceEntity nu = (SubServiceEntity) mapper.mapToEntity(dto);
            SubServiceEntity merged = (SubServiceEntity) mapper.merge(nu, og.get());
            SubServiceEntity saved = repository.save(merged);
            return (SubServiceDto) mapper.mapToDto(saved);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public SubServiceDto deleteSubService(SubServiceDto dto) {
        try {
            Optional<SubServiceEntity> og = repository.findById(dto.getId());
            if (og.isEmpty()) {
                throw new EntityNotFoundException("Sub Service with id %d not found".formatted(dto.getId()));
            }

            // detach relations
            if (!og.get().getServices().isEmpty()) {
                for (ServiceEntity service : og.get().getServices()) {
                    service.getSubServices().remove(og.get());
                }
                og.get().setServices(new HashSet<>());
            }

            if (og.get().getForm() != null) {
                og.get().setForm(null);
            }

            if (og.get().getWorkFlowGroup() != null) {
                og.get().setWorkFlowGroup(null);
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
