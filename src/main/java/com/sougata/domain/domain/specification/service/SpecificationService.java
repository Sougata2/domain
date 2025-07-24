package com.sougata.domain.domain.specification.service;

import com.sougata.domain.domain.specification.dto.SpecificationDto;

import java.util.List;

public interface SpecificationService {
    List<SpecificationDto> findAll();

    List<SpecificationDto> findByActivityId(Long id);

    SpecificationDto findById(Long id);

    SpecificationDto create(SpecificationDto dto);

    SpecificationDto update(SpecificationDto dto);

    SpecificationDto delete(SpecificationDto dto);
}
