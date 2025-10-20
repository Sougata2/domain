package com.sougata.domain.domain.devices.service;

import com.sougata.domain.domain.devices.dto.DeviceDto;

import java.util.List;
import java.util.Map;

public interface DeviceService {
    List<DeviceDto> findByReferenceNumber(String referenceNumber);

    List<Map<String, Object>> findDeviceWithJobByApplicationReferenceNumber(String referenceNumber);

    DeviceDto findById(Long id);

    DeviceDto create(DeviceDto dto);

    DeviceDto update(DeviceDto dto);

    DeviceDto delete(DeviceDto dto);
}
