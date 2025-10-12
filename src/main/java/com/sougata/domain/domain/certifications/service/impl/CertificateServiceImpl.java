package com.sougata.domain.domain.certifications.service.impl;

import com.sougata.domain.domain.application.entity.ApplicationEntity;
import com.sougata.domain.domain.application.repository.ApplicationRepository;
import com.sougata.domain.domain.certifications.dto.CertificationDto;
import com.sougata.domain.domain.certifications.entity.CertificationEntity;
import com.sougata.domain.domain.certifications.repository.CertificationRepository;
import com.sougata.domain.domain.certifications.service.CertificationService;
import com.sougata.domain.file.entity.FileEntity;
import com.sougata.domain.file.repository.FileRepository;
import com.sougata.domain.mapper.RelationalMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificationService {
    private final ApplicationRepository applicationRepository;
    private final CertificationRepository repository;
    private final FileRepository fileRepository;
    private final RelationalMapper mapper;

    @Override
    public List<CertificationDto> findByApplicationReferenceNumber(String referenceNumber) {
        try {
            Optional<ApplicationEntity> application = applicationRepository.findByReferenceNumber(referenceNumber);
            if (application.isEmpty()) {
                throw new EntityNotFoundException("Application with reference number %s is not found".formatted(referenceNumber));
            }
            List<CertificationEntity> entities = repository.findByApplicationReferenceNumber(referenceNumber);
            return entities.stream().map(e -> (CertificationDto) mapper.mapToDto(e)).toList();
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CertificationDto findById(Long id) {
        try {
            Optional<CertificationEntity> entity = repository.findById(id);
            if (entity.isEmpty()) {
                throw new EntityNotFoundException("Certification with id %s is not found".formatted(id));
            }
            return (CertificationDto) mapper.mapToDto(entity.get());
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CertificationDto findByCertificateNumber(String certificateNumber) {
        try {
            Optional<CertificationEntity> entity = repository.findByCertificateNumber(certificateNumber);
            if (entity.isEmpty()) {
                throw new EntityNotFoundException("Certification with Certificate Number %s is not found".formatted(certificateNumber));
            }
            return (CertificationDto) mapper.mapToDto(entity.get());
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public CertificationDto create(CertificationDto dto) {
        try {
            Optional<ApplicationEntity> application = applicationRepository.findByReferenceNumber(dto.getApplication().getReferenceNumber());
            if (application.isEmpty()) {
                throw new EntityNotFoundException("Application with reference number %s is not found".formatted(dto.getApplication().getReferenceNumber()));
            }
            Optional<FileEntity> file = fileRepository.findById(dto.getFile().getId());
            if (file.isEmpty()) {
                throw new EntityNotFoundException("File with id %s is not found".formatted(dto.getFile().getId()));
            }
            CertificationEntity entity = (CertificationEntity) mapper.mapToEntity(dto);
            entity.setApplication(application.get());
            entity.setFile(file.get());
            CertificationEntity saved = repository.save(entity);

            String certificateNumber = generatedCertificateNumber(entity);
            saved.setCertificateNumber(certificateNumber);
            saved = repository.save(saved);
            return (CertificationDto) mapper.mapToDto(saved);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public CertificationDto update(CertificationDto dto) {
        try {
            Optional<CertificationEntity> og = repository.findById(dto.getId());
            if (og.isEmpty()) {
                throw new EntityNotFoundException("Certification with id %s is not found".formatted(dto.getId()));
            }

            if (dto.getApplication() != null && dto.getApplication().getReferenceNumber() != null) {
                Optional<ApplicationEntity> application = applicationRepository.findByReferenceNumber(dto.getApplication().getReferenceNumber());
                if (application.isEmpty()) {
                    throw new EntityNotFoundException("Application with reference number %s is not found".formatted(dto.getApplication().getReferenceNumber()));
                }
            }
            if (dto.getFile() != null && dto.getFile().getId() != null) {
                Optional<FileEntity> file = fileRepository.findById(dto.getFile().getId());
                if (file.isEmpty()) {
                    throw new EntityNotFoundException("File with id %s is not found".formatted(dto.getFile().getId()));
                }
            }

            CertificationEntity nu = (CertificationEntity) mapper.mapToEntity(dto);
            CertificationEntity merged = (CertificationEntity) mapper.merge(nu, og.get());

            CertificationEntity saved = repository.save(merged);
            return (CertificationDto) mapper.mapToDto(saved);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public CertificationDto delete(CertificationDto dto) {
        try {
            Optional<CertificationEntity> entity = repository.findById(dto.getId());
            if (entity.isEmpty()) {
                throw new EntityNotFoundException("Certification with id %s is not found".formatted(dto.getId()));
            }

            repository.delete(entity.get());
            return dto;
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String generatedCertificateNumber(CertificationEntity entity) {
        try {
            Optional<ApplicationEntity> application = applicationRepository.findByReferenceNumber(entity.getApplication().getReferenceNumber());
            if (application.isEmpty()) {
                throw new EntityNotFoundException("Application with reference number %s is not found".formatted(entity.getApplication().getReferenceNumber()));
            }

            Optional<FileEntity> file = fileRepository.findById(entity.getFile().getId());
            if (file.isEmpty()) {
                throw new EntityNotFoundException("File with id %s is not found".formatted(entity.getFile().getId()));
            }

            String prefix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            return prefix + application.get().getId() + file.get().getId() + entity.getId();
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
