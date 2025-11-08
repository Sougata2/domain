package com.sougata.domain.domain.status.service;

import com.sougata.domain.domain.status.dto.StatusDto;

import java.util.List;

public interface StatusService {
    List<StatusDto> findAll();

    StatusDto findById(Long id);

    StatusDto findByStatusName(String name);

    StatusDto create(StatusDto dto);

    StatusDto update(StatusDto dto);

    StatusDto delete(StatusDto dto);
}
