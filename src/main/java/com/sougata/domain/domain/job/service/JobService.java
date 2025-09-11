package com.sougata.domain.domain.job.service;

import com.sougata.domain.domain.job.dto.JobDto;

public interface JobService {
    JobDto findJobByDeviceId(Long deviceId);

    JobDto findById(Long id);

    JobDto create(JobDto dto);

    JobDto update(JobDto dto);

    JobDto delete(JobDto dto);
}
