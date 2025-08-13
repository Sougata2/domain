package com.sougata.domain.domain.document.service.impl;

import com.sougata.domain.domain.application.entity.ApplicationEntity;
import com.sougata.domain.domain.application.repository.ApplicationRepository;
import com.sougata.domain.domain.document.dto.DocumentDto;
import com.sougata.domain.domain.document.entity.DocumentEntity;
import com.sougata.domain.domain.document.repository.DocumentRepository;
import com.sougata.domain.domain.document.service.DocumentService;
import com.sougata.domain.file.entity.FileEntity;
import com.sougata.domain.file.repository.FileRepository;
import com.sougata.domain.mapper.RelationalMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final ApplicationRepository applicationRepository;
    private final FileRepository fileRepository;
    private final DocumentRepository repository;
    private final RelationalMapper mapper;

    @Override
    public List<DocumentDto> findByReferenceNumber(String referenceNumber) {
        try {
            Optional<ApplicationEntity> application = applicationRepository.findByReferenceNumber(referenceNumber);
            if (application.isEmpty()) {
                throw new EntityNotFoundException("Application Entity with reference number %s is not found".formatted(referenceNumber));
            }
            List<DocumentEntity> entities = repository.findByReferenceNumber(referenceNumber);
            return entities.stream().map(e -> (DocumentDto) mapper.mapToDto(e)).toList();
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DocumentDto findById(Long id) {
        try {
            Optional<DocumentEntity> document = repository.findById(id);
            if (document.isEmpty()) {
                throw new EntityNotFoundException("Document Entity with id %s is not found".formatted(id));
            }
            return (DocumentDto) mapper.mapToDto(document.get());
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public DocumentDto create(DocumentDto dto) {
        try {
            Optional<ApplicationEntity> application = applicationRepository.findByReferenceNumber(dto.getApplication().getReferenceNumber());
            if (application.isEmpty()) {
                throw new EntityNotFoundException("Application Entity with reference number %s is not found".formatted(dto.getApplication().getReferenceNumber()));
            }
            Optional<FileEntity> fileEntity = fileRepository.findById(dto.getFile().getId());
            if (fileEntity.isEmpty()) {
                throw new EntityNotFoundException("File with id %d is not found".formatted(dto.getFile().getId()));
            }
            DocumentEntity entity = (DocumentEntity) mapper.mapToEntity(dto);

            // attaching the relations
            entity.setApplication(application.get());
            entity.setFile(fileEntity.get());

            DocumentEntity saved = repository.save(entity);
            return (DocumentDto) mapper.mapToDto(saved);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public DocumentDto update(DocumentDto dto) {
        try {
            Optional<ApplicationEntity> application = applicationRepository.findByReferenceNumber(dto.getApplication().getReferenceNumber());
            if (application.isEmpty()) {
                throw new EntityNotFoundException("Application Entity with reference number %s is not found".formatted(dto.getApplication().getReferenceNumber()));
            }
            Optional<FileEntity> fileEntity = fileRepository.findById(dto.getFile().getId());
            if (fileEntity.isEmpty()) {
                throw new EntityNotFoundException("File with id %d is not found".formatted(dto.getFile().getId()));
            }

            Optional<DocumentEntity> og = repository.findById(dto.getId());
            if (og.isEmpty()) {
                throw new EntityNotFoundException("Document Entity with id %d is not found".formatted(dto.getId()));
            }
            DocumentEntity nu = (DocumentEntity) mapper.mapToEntity(dto);
            DocumentEntity merged = (DocumentEntity) mapper.merge(nu, og.get());
            DocumentEntity saved = repository.save(merged);
            return (DocumentDto) mapper.mapToDto(saved);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public DocumentDto delete(DocumentDto dto) {
        try {
            Optional<ApplicationEntity> application = applicationRepository.findByReferenceNumber(dto.getApplication().getReferenceNumber());
            if (application.isEmpty()) {
                throw new EntityNotFoundException("Application Entity with reference number %s is not found".formatted(dto.getApplication().getReferenceNumber()));
            }
            Optional<FileEntity> fileEntity = fileRepository.findById(dto.getFile().getId());
            if (fileEntity.isEmpty()) {
                throw new EntityNotFoundException("File with id %d is not found".formatted(dto.getFile().getId()));
            }
            Optional<DocumentEntity> og = repository.findById(dto.getId());
            if (og.isEmpty()) {
                throw new EntityNotFoundException("Document Entity with id %d is not found".formatted(dto.getId()));
            }

            // detach relations
            if (og.get().getApplication() != null) {
                og.get().getApplication().getDocuments().remove(og.get());
                og.get().setApplication(null);
            }
            if (og.get().getFile() != null) {
                og.get().setFile(null);
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
