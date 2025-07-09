package com.sougata.domain.domain.forms.service.impl;

import com.sougata.domain.domain.forms.dto.FormDto;
import com.sougata.domain.domain.forms.entity.FormEntity;
import com.sougata.domain.domain.forms.repository.FormRepository;
import com.sougata.domain.domain.forms.service.FormService;
import com.sougata.domain.mapper.RelationalMapper;
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
public class FormServiceImpl implements FormService {
    private final FormRepository repository;
    private final RelationalMapper mapper;

    @Override
    public List<FormDto> findAllForms() {
        try {
            List<FormEntity> list = repository.findAll();
            return list.stream().map(e -> (FormDto) mapper.mapToDto(e)).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FormDto findFormById(Long id) {
        try {
            Optional<FormEntity> entity = repository.findById(id);
            if (entity.isEmpty()) {
                throw new EntityNotFoundException("Form with id %d not found".formatted(id));
            }
            return (FormDto) mapper.mapToDto(entity.get());
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public FormDto createForm(FormDto dto) {
        try {
            FormEntity entity = (FormEntity) mapper.mapToEntity(dto);
            FormEntity saved = repository.save(entity);
            return (FormDto) mapper.mapToDto(saved);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public FormDto updateForm(FormDto dto) {
        try {
            Optional<FormEntity> og = repository.findById(dto.getId());

            if (og.isEmpty()) {
                throw new EntityNotFoundException("Form with id %d not found".formatted(dto.getId()));
            }

            FormEntity nu = (FormEntity) mapper.mapToEntity(dto);
            FormEntity merged = (FormEntity) mapper.merge(nu, og.get());

            FormEntity saved = repository.save(merged);
            return (FormDto) mapper.mapToDto(saved);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public FormDto deleteForm(FormDto dto) {
        try {
            Optional<FormEntity> og = repository.findById(dto.getId());
            if (og.isEmpty()) {
                throw new EntityNotFoundException("Form with id %d not found".formatted(dto.getId()));
            }

            // detach relations
            if (!og.get().getSubServices().isEmpty()) {
                for (SubServiceEntity subService : og.get().getSubServices()) {
                    if (subService.getForm().getId().equals(og.get().getId())) {
                        subService.setForm(null);
                    }
                }
                og.get().setSubServices(new HashSet<>());
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
