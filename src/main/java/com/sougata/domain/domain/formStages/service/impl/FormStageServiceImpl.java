package com.sougata.domain.domain.formStages.service.impl;

import com.sougata.domain.domain.formStages.dto.FormStageDto;
import com.sougata.domain.domain.formStages.entity.FormStageEntity;
import com.sougata.domain.domain.formStages.repository.FormStageRepository;
import com.sougata.domain.domain.formStages.service.FormStageService;
import com.sougata.domain.domain.forms.entity.FormEntity;
import com.sougata.domain.domain.forms.repository.FormRepository;
import com.sougata.domain.mapper.RelationalMapper;
import com.sougata.domain.menu.entity.MenuEntity;
import com.sougata.domain.menu.repository.MenuRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FormStageServiceImpl implements FormStageService {
    private final RelationalMapper mapper;
    private final FormStageRepository repository;
    private final FormRepository formRepository;
    private final MenuRepository menuRepository;


    @Override
    public List<FormStageDto> findByReferenceNumber(String referenceNumber) {
        List<FormStageEntity> entities = repository.findByReferenceNumber(referenceNumber);
        return entities.stream().map(e -> (FormStageDto) mapper.mapToDto(e)).toList();
    }

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
        try {
            Optional<MenuEntity> menu = menuRepository.findById(dto.getMenu().getId());
            if (menu.isEmpty()) {
                throw new EntityNotFoundException("Menu with id %d not found".formatted(dto.getMenu().getId()));
            }
            Optional<FormEntity> form = formRepository.findById(dto.getForm().getId());
            if (form.isEmpty()) {
                throw new EntityNotFoundException("Form with id %d not found".formatted(dto.getForm().getId()));
            }
            FormStageEntity entity = new FormStageEntity();
            entity.setForm(form.get());
            entity.setMenu(menu.get());
            entity.setStageOrder(dto.getStageOrder());
            FormStageEntity saved = repository.save(entity);
            return (FormStageDto) mapper.mapToDto(saved);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        if (og.get().getForm() != null) {
            og.get().getForm().getStages().remove(og.get());
            og.get().setForm(null);
        }

        if (og.get().getMenu() != null) {
            og.get().getMenu().getStages().remove(og.get());
            og.get().setMenu(null);
        }

        repository.delete(og.get());
        return dto;
    }
}
