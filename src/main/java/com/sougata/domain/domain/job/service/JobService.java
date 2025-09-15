package com.sougata.domain.domain.job.service;

import com.sougata.domain.domain.job.dto.JobDto;
import com.sougata.domain.domain.job.dto.JobProcessDto;
import com.sougata.domain.domain.jobWorkFlowHistory.dto.JobWorkFlowHistoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface JobService {
    JobDto findByDeviceId(Long deviceId);

    List<JobDto> findByApplicationReferenceNumber(String referenceNumber);

    Page<JobDto> search(Map<String, String> filters, Pageable pageable);

    JobDto findById(Long id);

    JobDto create(JobDto dto);

    JobDto update(JobDto dto);

    JobDto delete(JobDto dto);

    JobWorkFlowHistoryDto doNext(JobProcessDto dto);
}
