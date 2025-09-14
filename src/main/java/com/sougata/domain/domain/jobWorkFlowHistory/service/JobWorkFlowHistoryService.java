package com.sougata.domain.domain.jobWorkFlowHistory.service;

import com.sougata.domain.domain.jobWorkFlowHistory.dto.JobWorkFlowHistoryDto;
import com.sougata.domain.user.dto.UserDto;

import java.util.List;

public interface JobWorkFlowHistoryService {
    List<JobWorkFlowHistoryDto> findByJobId(Long jobId);

    JobWorkFlowHistoryDto findById(Long id);

    List<UserDto> getRegressiveUser(Long jobId, Long targetRoleId);

    JobWorkFlowHistoryDto create(JobWorkFlowHistoryDto dto);

    JobWorkFlowHistoryDto update(JobWorkFlowHistoryDto dto);

    JobWorkFlowHistoryDto delete(JobWorkFlowHistoryDto dto);
}
