package com.sougata.domain.subService.service;

import com.sougata.domain.subService.dto.SubServiceDto;

import java.util.List;

public interface SubServiceService {
    List<SubServiceDto> findAllSubServices();

    SubServiceDto findSubServiceById(Long id);

    SubServiceDto createSubService(SubServiceDto dto);

    SubServiceDto updateSubService(SubServiceDto dto);

    SubServiceDto deleteSubService(SubServiceDto dto);
}
