package com.sougata.domain.domain.lab.service;

import com.sougata.domain.domain.lab.dto.LabDto;

import java.util.List;

public interface LabService {
    List<LabDto> findAll();

    LabDto findById(Long id);

    LabDto create(LabDto dto);

    LabDto update(LabDto dto);

    LabDto delete(LabDto dto);
}
