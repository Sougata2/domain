package com.sougata.domain.domain.application.service;

import com.sougata.domain.domain.application.dto.ApplicationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ApplicationService {
    List<ApplicationDto> findAll();

    ApplicationDto findById(Long id);

    ApplicationDto findByReferenceNumber(String applicationId);

    Page<ApplicationDto> findByStatusNameAndApplicantId(String statusName, Long userId, Pageable pageable);

    ApplicationDto create(ApplicationDto dto) throws Exception;

    ApplicationDto update(ApplicationDto dto);

    ApplicationDto delete(ApplicationDto dto);
}
