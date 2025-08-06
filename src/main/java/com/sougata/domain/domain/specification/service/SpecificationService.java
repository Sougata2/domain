package com.sougata.domain.domain.specification.service;

import com.sougata.domain.domain.specification.dto.SpecificationDto;

import java.util.List;
import java.util.Set;

public interface SpecificationService {
    List<SpecificationDto> findAll();

    List<SpecificationDto> findByActivityIds(Set<Long> id);

    SpecificationDto findById(Long id);

    SpecificationDto create(SpecificationDto dto);

    SpecificationDto update(SpecificationDto dto);

    SpecificationDto delete(SpecificationDto dto);
}
