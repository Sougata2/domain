package com.sougata.domain.domain.viewComponent.service;

import com.sougata.domain.domain.viewComponent.dto.ViewComponentDto;

import java.util.List;

public interface ViewComponentService {
    List<ViewComponentDto> findAll();

    List<ViewComponentDto> findAllByRoleIdAndApplicationType(Long roleId, String applicationType);

    ViewComponentDto findById(Long id);

    ViewComponentDto create(ViewComponentDto dto);

    ViewComponentDto update(ViewComponentDto dto);

    ViewComponentDto delete(ViewComponentDto dto);
}
