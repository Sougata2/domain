package com.sougata.domain.domain.labTestRecord.service;

import com.sougata.domain.domain.labTestRecord.dto.LabTestRecordDto;

public interface LabTestRecordService {
    LabTestRecordDto findByTemplateIdAndJobId(Long templateId, Long jobId);

    LabTestRecordDto findById(Long id);

    LabTestRecordDto create(LabTestRecordDto dto);

    LabTestRecordDto update(LabTestRecordDto dto);

    LabTestRecordDto delete(LabTestRecordDto dto);
}
