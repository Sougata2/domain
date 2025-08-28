package com.sougata.domain.domain.workflowHistory.service;

import com.sougata.domain.domain.workflowHistory.dto.WorkFlowHistoryDto;
import com.sougata.domain.user.dto.UserDto;

import java.util.List;

public interface WorkFlowHistoryService {

    List<WorkFlowHistoryDto> findByReferenceNumber(String referenceNumber);

    WorkFlowHistoryDto findById(Long id);

    List<UserDto> getRegressiveUser(String referenceNumber, Long targetRoleId);

    WorkFlowHistoryDto create(WorkFlowHistoryDto dto);

    WorkFlowHistoryDto update(WorkFlowHistoryDto dto);

    WorkFlowHistoryDto delete(WorkFlowHistoryDto dto);
}
