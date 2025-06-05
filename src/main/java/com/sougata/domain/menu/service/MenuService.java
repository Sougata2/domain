package com.sougata.domain.menu.service;

import com.sougata.domain.menu.dto.MenuDto;

import java.util.List;

public interface MenuService {
    List<MenuDto> findAllTopLevelMenus();

    List<MenuDto> findAllMenus();

    MenuDto createMenu(MenuDto menuDto);

    MenuDto updateMenu(MenuDto menuDto);

    List<MenuDto> bulkUpdateMenus(List<MenuDto> dtos);

    MenuDto deleteMenu(MenuDto menuDto);

    MenuDto findMenuById(Long menuId);
}
