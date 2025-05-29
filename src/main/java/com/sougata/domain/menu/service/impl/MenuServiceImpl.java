package com.sougata.domain.menu.service.impl;

import com.sougata.domain.mapper.RelationalMapper;
import com.sougata.domain.menu.dto.MenuDto;
import com.sougata.domain.menu.entity.MenuEntity;
import com.sougata.domain.menu.repository.MenuRepository;
import com.sougata.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository repository;

    @Override
    public List<MenuDto> findAllTopLevelMenus() {
        List<MenuEntity> entities = repository.findAllMenusWithSubMenus();
        return entities.stream().map(e -> (MenuDto) RelationalMapper.mapToDto(e)).toList();
    }

    @Override
    public MenuDto createMenu(MenuDto menuDto) {
        return null;
    }
}
