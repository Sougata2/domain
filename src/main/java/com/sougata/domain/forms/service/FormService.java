package com.sougata.domain.forms.service;


import com.sougata.domain.forms.dto.FormDto;

import java.util.List;

public interface FormService {
    List<FormDto> findAllForms();

    FormDto findFormById(Long id);

    FormDto createForm(FormDto dto);

    FormDto updateForm(FormDto dto);

    FormDto deleteForm(FormDto dto);
}
