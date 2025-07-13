package com.sougata.domain.domain.application.service;

import com.sougata.domain.domain.application.dto.ApplicationDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ApplicationService {
    List<ApplicationDto> findAll();

    ApplicationDto findById(Long id);

    ApplicationDto findByReferenceNumber(String applicationId);

    Page<ApplicationDto> findByStatusNameAndUserId(String statusName, Long userId, int page, int size);

    ApplicationDto create(ApplicationDto dto) throws Exception;

    ApplicationDto update(ApplicationDto dto);

    ApplicationDto delete(ApplicationDto dto);
}
