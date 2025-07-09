package com.sougata.domain.domain.activity.service;

import com.sougata.domain.domain.activity.dto.ActivityDto;

import java.util.List;

public interface ActivityService {
    List<ActivityDto> findAll();

    ActivityDto findById(Long id);

    ActivityDto create(ActivityDto dto);

    ActivityDto update(ActivityDto dto);

    ActivityDto delete(ActivityDto dto);
}
