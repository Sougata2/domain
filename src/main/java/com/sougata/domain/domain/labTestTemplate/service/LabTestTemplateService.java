package com.sougata.domain.domain.labTestTemplate.service;

import com.sougata.domain.domain.labTestTemplate.dto.LabTestTemplateDto;

import java.util.List;

public interface LabTestTemplateService {
    List<LabTestTemplateDto> findByJobId(Long jobId);

    List<LabTestTemplateDto> findBySubServiceId(Long subServiceId);

    List<LabTestTemplateDto> findAll();

    LabTestTemplateDto findById(Long id);

    LabTestTemplateDto create(LabTestTemplateDto dto);

    LabTestTemplateDto update(LabTestTemplateDto dto);

    LabTestTemplateDto delete(LabTestTemplateDto dto);
}
