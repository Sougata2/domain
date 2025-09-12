package com.sougata.domain.domain.job.service;

import com.sougata.domain.domain.job.dto.JobDto;

import java.util.List;

public interface JobService {
    JobDto findByDeviceId(Long deviceId);

    List<JobDto> findByApplicationReferenceNumber(String referenceNumber);

    JobDto findById(Long id);

    JobDto create(JobDto dto);

    JobDto update(JobDto dto);

    JobDto delete(JobDto dto);
}
