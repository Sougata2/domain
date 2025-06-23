package com.sougata.domain.services.service.impl;

import com.sougata.domain.mapper.RelationalMapper;
import com.sougata.domain.services.dto.ServiceDto;
import com.sougata.domain.services.entity.ServiceEntity;
import com.sougata.domain.services.repository.ServiceRepository;
import com.sougata.domain.services.service.ServicesService;
import com.sougata.domain.subService.entity.SubServiceEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServicesServiceImpl implements ServicesService {
    private final ServiceRepository repository;
    private final RelationalMapper mapper;

    @Override
    public List<ServiceDto> findAllServices() {
        List<ServiceEntity> services = repository.findAll();
        return services.stream().map(e -> (ServiceDto) mapper.mapToDto(e)).toList();
    }

    @Override
    public ServiceDto findServiceById(Long id) {
        try {
            Optional<ServiceEntity> entity = repository.findById(id);
            if (entity.isEmpty()) {
                throw new EntityNotFoundException("Service Entity with id %d not found".formatted(id));
            }
            return (ServiceDto) mapper.mapToDto(entity.get());
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public ServiceDto createService(ServiceDto dto) {
        try {
            ServiceEntity entity = (ServiceEntity) mapper.mapToEntity(dto);
            ServiceEntity saved = repository.save(entity);
            return (ServiceDto) mapper.mapToDto(saved);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public ServiceDto updateService(ServiceDto dto) {
        try {
            Optional<ServiceEntity> og = repository.findById(dto.getId());
            if (og.isEmpty()) {
                throw new EntityNotFoundException("Service Entity with id %d not found".formatted(dto.getId()));
            }

            ServiceEntity nu = (ServiceEntity) mapper.mapToEntity(dto);
            ServiceEntity merged = (ServiceEntity) mapper.merge(nu, og.get());

            ServiceEntity saved = repository.save(merged);
            return (ServiceDto) mapper.mapToDto(saved);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public ServiceDto deleteService(ServiceDto dto) {
        try {
            Optional<ServiceEntity> entity = repository.findById(dto.getId());
            if (entity.isEmpty()) {
                throw new EntityNotFoundException("Service Entity with id %d not found".formatted(dto.getId()));
            }

            //detach relations
            if (!entity.get().getSubServices().isEmpty()) {
                for (SubServiceEntity subService : entity.get().getSubServices()) {
                    subService.getServices().remove(entity.get());
                }
                entity.get().setSubServices(new HashSet<>());
            }

            repository.delete(entity.get());
            return dto;
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
