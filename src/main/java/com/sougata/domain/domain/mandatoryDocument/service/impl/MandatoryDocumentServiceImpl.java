package com.sougata.domain.domain.mandatoryDocument.service.impl;

import com.sougata.domain.domain.forms.entity.FormEntity;
import com.sougata.domain.domain.forms.repository.FormRepository;
import com.sougata.domain.domain.mandatoryDocument.dto.MandatoryDocumentsDto;
import com.sougata.domain.domain.mandatoryDocument.entity.MandatoryDocumentsEntity;
import com.sougata.domain.domain.mandatoryDocument.repository.MandatoryDocumentsRepository;
import com.sougata.domain.domain.mandatoryDocument.service.MandatoryDocumentsService;
import com.sougata.domain.mapper.RelationalMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MandatoryDocumentServiceImpl implements MandatoryDocumentsService {
    private final MandatoryDocumentsRepository repository;
    private final FormRepository formRepository;
    private final RelationalMapper mapper;

    @Override
    public List<MandatoryDocumentsDto> findAll() {
        try {
            List<MandatoryDocumentsEntity> entities = repository.findAll();
            return entities.stream().map(e -> (MandatoryDocumentsDto) mapper.mapToDto(e)).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MandatoryDocumentsDto> findByFormId(Long formId) {
        try {
            Optional<FormEntity> form = formRepository.findById(formId);
            if (form.isEmpty()) {
                throw new EntityNotFoundException("Form Entity with Id %d not found".formatted(formId));
            }
            List<MandatoryDocumentsEntity> entities = repository.findByFormId(formId);
            return entities.stream().map(e -> (MandatoryDocumentsDto) mapper.mapToDto(e)).toList();
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MandatoryDocumentsDto findById(Long id) {
        try {
            Optional<MandatoryDocumentsEntity> entity = repository.findById(id);
            if (entity.isEmpty()) {
                throw new EntityNotFoundException("MandatoryDocument Entity with Id %d not found".formatted(id));
            }
            return (MandatoryDocumentsDto) mapper.mapToDto(entity.get());
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public MandatoryDocumentsDto create(MandatoryDocumentsDto dto) {
        try {
            Optional<FormEntity> form = formRepository.findById(dto.getForm().getId());
            if (form.isEmpty()) {
                throw new EntityNotFoundException("Form Entity with Id %d not found".formatted(dto.getForm().getId()));
            }
            MandatoryDocumentsEntity entity = (MandatoryDocumentsEntity) mapper.mapToEntity(dto);
            entity.setForm(form.get());
            MandatoryDocumentsEntity saved = repository.save(entity);
            return (MandatoryDocumentsDto) mapper.mapToDto(saved);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public MandatoryDocumentsDto update(MandatoryDocumentsDto dto) {
        try {
            Optional<FormEntity> form = formRepository.findById(dto.getForm().getId());
            if (form.isEmpty()) {
                throw new EntityNotFoundException("Form Entity with Id %d not found".formatted(dto.getForm().getId()));
            }
            Optional<MandatoryDocumentsEntity> og = repository.findById(dto.getId());
            if (og.isEmpty()) {
                throw new EntityNotFoundException("Mandatory Document Entity with Id %d not found".formatted(dto.getId()));
            }
            MandatoryDocumentsEntity nu = (MandatoryDocumentsEntity) mapper.mapToEntity(dto);
            MandatoryDocumentsEntity merged = (MandatoryDocumentsEntity) mapper.merge(nu, og.get());
            MandatoryDocumentsEntity saved = repository.save(merged);
            return (MandatoryDocumentsDto) mapper.mapToDto(saved);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public MandatoryDocumentsDto delete(MandatoryDocumentsDto dto) {
        try {
            Optional<MandatoryDocumentsEntity> og = repository.findById(dto.getId());
            if (og.isEmpty()) {
                throw new EntityNotFoundException("Mandatory Document Entity with Id %d not found".formatted(dto.getId()));
            }

            //detach relations
            if (og.get().getForm() != null) {
                og.get().getForm().getMandatoryDocuments().remove(og.get());
                og.get().setForm(null);
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
