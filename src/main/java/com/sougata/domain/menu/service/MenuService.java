package com.sougata.domain.menu.service;

import com.sougata.domain.menu.dto.MenuDto;

import java.util.List;

public interface MenuService {
    List<MenuDto> findAllTopLevelMenus();

    MenuDto createMenu(MenuDto menuDto);
}
