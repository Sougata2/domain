package com.sougata.domain.domain.workFlowAction.service;

import com.sougata.domain.domain.status.dto.StatusDto;
import com.sougata.domain.domain.workFlowAction.dto.WorkFlowActionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface WorkFlowActionService {
    List<WorkFlowActionDto> findByStatusId(Long statusId);

    List<WorkFlowActionDto> findByReferenceNumber(String referenceNumber);

    List<WorkFlowActionDto> findAll();

    Page<WorkFlowActionDto> search(Map<String, String> filter, Pageable pageable);

    List<StatusDto> findTargetStatusByCurrentStatus(Long statusId);

    WorkFlowActionDto findById(Long id);

    WorkFlowActionDto create(WorkFlowActionDto dto);

    WorkFlowActionDto update(WorkFlowActionDto dto);

    WorkFlowActionDto delete(WorkFlowActionDto dto);
}
