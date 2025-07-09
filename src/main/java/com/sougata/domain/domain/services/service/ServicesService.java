package com.sougata.domain.domain.services.service;

import com.sougata.domain.domain.services.dto.ServiceDto;

import java.util.List;

public interface ServicesService {
    List<ServiceDto> findAllServices();

    ServiceDto findServiceById(Long id);

    ServiceDto createService(ServiceDto dto);

    ServiceDto updateService(ServiceDto dto);

    ServiceDto deleteService(ServiceDto dto);
}
