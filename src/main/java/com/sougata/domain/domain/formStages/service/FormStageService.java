package com.sougata.domain.domain.formStages.service;

import com.sougata.domain.domain.formStages.dto.FormStageDto;

import java.util.List;

public interface FormStageService {
    List<FormStageDto> findByFormId(Long formId);

    FormStageDto findById(Long id);

    FormStageDto create(FormStageDto dto);

    FormStageDto update(FormStageDto dto);

    FormStageDto delete(FormStageDto dto);
}
