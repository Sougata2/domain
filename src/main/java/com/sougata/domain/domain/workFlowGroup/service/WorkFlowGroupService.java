package com.sougata.domain.domain.workFlowGroup.service;

import com.sougata.domain.domain.workFlowGroup.dto.WorkFlowGroupDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface WorkFlowGroupService {
    Page<WorkFlowGroupDto> search(Map<String, String> filter, Pageable pageable);

    List<WorkFlowGroupDto> findAll();

    WorkFlowGroupDto findById(Long id);

    WorkFlowGroupDto create(WorkFlowGroupDto dto);

    WorkFlowGroupDto update(WorkFlowGroupDto dto);

    WorkFlowGroupDto delete(WorkFlowGroupDto dto);
}
