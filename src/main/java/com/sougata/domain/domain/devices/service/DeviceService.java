package com.sougata.domain.domain.devices.service;

import com.sougata.domain.domain.devices.dto.DeviceDto;

import java.util.List;

public interface DeviceService {
    List<DeviceDto> findByReferenceNumber(String referenceNumber);

    DeviceDto findById(Long id);

    DeviceDto create(DeviceDto dto);

    DeviceDto update(DeviceDto dto);

    DeviceDto delete(DeviceDto dto);
}
