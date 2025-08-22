package com.sougata.domain.domain.workflowHistory.service;

import com.sougata.domain.domain.workflowHistory.dto.WorkFlowHistoryDto;

import java.util.List;

public interface WorkFlowHistoryService {

    List<WorkFlowHistoryDto> findByReferenceNumber(String referenceNumber);

    WorkFlowHistoryDto findById(Long id);

    WorkFlowHistoryDto create(WorkFlowHistoryDto dto);

    WorkFlowHistoryDto update(WorkFlowHistoryDto dto);

    WorkFlowHistoryDto delete(WorkFlowHistoryDto dto);
}
