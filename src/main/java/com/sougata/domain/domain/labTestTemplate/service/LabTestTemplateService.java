package com.sougata.domain.domain.labTestTemplate.service;

import com.sougata.domain.domain.labTestTemplate.dto.LabTestTemplateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface LabTestTemplateService {
    List<LabTestTemplateDto> findByJobId(Long jobId);

    List<LabTestTemplateDto> findBySubServiceId(Long subServiceId);

    List<LabTestTemplateDto> findAll();

    Page<LabTestTemplateDto> search(Map<String, Object> filters, Pageable pageable);

    LabTestTemplateDto findById(Long id);

    LabTestTemplateDto create(LabTestTemplateDto dto);

    LabTestTemplateDto update(LabTestTemplateDto dto);

    LabTestTemplateDto delete(LabTestTemplateDto dto);
}
