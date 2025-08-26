package com.sougata.domain.domain.application.service;

import com.sougata.domain.domain.application.dto.ApplicationDto;
import com.sougata.domain.domain.application.dto.ApplicationProcessDto;
import com.sougata.domain.domain.workflowHistory.dto.WorkFlowHistoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ApplicationService {
    List<ApplicationDto> findAll();

    ApplicationDto findById(Long id);

    ApplicationDto findByReferenceNumber(String applicationId);

    Page<ApplicationDto> findByStatusNameAndApplicantId(String statusName, Long userId, Pageable pageable);

    Page<ApplicationDto> search(Map<String, String> filter, Pageable pageable);

    ApplicationDto create(ApplicationDto dto) throws Exception;

    ApplicationDto update(ApplicationDto dto);

    ApplicationDto delete(ApplicationDto dto);

    WorkFlowHistoryDto doNext(ApplicationProcessDto dto);
}
