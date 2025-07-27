package com.sougata.domain.domain.formStages.service.impl;

import com.sougata.domain.domain.formStages.dto.FormStageDto;
import com.sougata.domain.domain.formStages.entity.FormStageEntity;
import com.sougata.domain.domain.formStages.repository.FormStageRepository;
import com.sougata.domain.domain.formStages.service.FormStageService;
import com.sougata.domain.domain.forms.entity.FormEntity;
import com.sougata.domain.domain.forms.repository.FormRepository;
import com.sougata.domain.mapper.RelationalMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FormStageServiceImpl implements FormStageService {
    private final RelationalMapper mapper;
    private final FormStageRepository repository;
    private final FormRepository formRepository;


    @Override
    public List<FormStageDto> findByFormId(Long formId) {
        try {
            Optional<FormEntity> formEntity = formRepository.findById(formId);
            if (formEntity.isEmpty()) {
                throw new EntityNotFoundException("Form with id %d not found".formatted(formId));
            }

            List<FormStageEntity> stages = repository.findByFormId(formId);
            return stages.stream().map(e -> (FormStageDto) mapper.mapToDto(e)).toList();
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FormStageDto findById(Long id) {
        Optional<FormStageEntity> entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new EntityNotFoundException("Stage with id %d not found".formatted(id));
        }
        return (FormStageDto) mapper.mapToDto(entity.get());
    }

    @Override
    @Transactional
    public FormStageDto create(FormStageDto dto) {
        FormStageEntity entity = (FormStageEntity) mapper.mapToEntity(dto);
        FormStageEntity saved = repository.save(entity);
        return (FormStageDto) mapper.mapToDto(saved);
    }

    @Override
    @Transactional
    public FormStageDto update(FormStageDto dto) {
        Optional<FormStageEntity> og = repository.findById(dto.getId());
        if (og.isEmpty()) {
            throw new EntityNotFoundException("Stage with id %d not found".formatted(dto.getId()));
        }
        FormStageEntity nu = (FormStageEntity) mapper.mapToEntity(dto);
        FormStageEntity merged = (FormStageEntity) mapper.merge(nu, og.get());
        FormStageEntity saved = repository.save(merged);
        return (FormStageDto) mapper.mapToDto(saved);
    }

    @Override
    @Transactional
    public FormStageDto delete(FormStageDto dto) {
        Optional<FormStageEntity> og = repository.findById(dto.getId());
        if (og.isEmpty()) {
            throw new EntityNotFoundException("Stage with id %d not found".formatted(dto.getId()));
        }

        // detach relations
        if (!og.get().getForms().isEmpty()) {
            for (FormEntity form : og.get().getForms()) {
                form.getStages().remove(og.get());
            }
            og.get().setForms(new HashSet<>());
        }
        repository.delete(og.get());
        return dto;
    }
}
