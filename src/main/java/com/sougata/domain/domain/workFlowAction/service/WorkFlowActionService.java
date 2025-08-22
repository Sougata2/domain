package com.sougata.domain.domain.workFlowAction.service;

import com.sougata.domain.domain.workFlowAction.dto.WorkFlowActionDto;

import java.util.List;

public interface WorkFlowActionService {
    List<WorkFlowActionDto> findByStatusId(Long statusId);

    List<WorkFlowActionDto> search();

    WorkFlowActionDto findById(Long id);

    WorkFlowActionDto create(WorkFlowActionDto dto);

    WorkFlowActionDto update(WorkFlowActionDto dto);

    WorkFlowActionDto delete(WorkFlowActionDto dto);
}
