package com.sougata.domain.menu.service.impl;

import com.sougata.domain.mapper.RelationalMapper;
import com.sougata.domain.menu.dto.MenuDto;
import com.sougata.domain.menu.entity.MenuEntity;
import com.sougata.domain.menu.repository.MenuRepository;
import com.sougata.domain.menu.service.MenuService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository repository;
    private final EntityManager entityManager;

    @Override
    public List<MenuDto> findAllTopLevelMenus() {
        List<MenuEntity> entities = repository.findAllMenusWithSubMenus();
        return entities.stream().map(e -> (MenuDto) RelationalMapper.mapToDto(e)).toList();
    }

    @Override
    public MenuDto createMenu(MenuDto menuDto) {
        MenuEntity entity = (MenuEntity) RelationalMapper.mapToEntity(menuDto);
        MenuEntity saved = repository.save(entity);
        return (MenuDto) RelationalMapper.mapToDto(saved);
    }

    @Override
    public MenuDto updateMenu(MenuDto menuDto) {
        MenuEntity og = repository.findById(menuDto.getId()).orElse(null);
        if (og == null) return null;
        MenuEntity nu = (MenuEntity) RelationalMapper.mapToEntity(menuDto);
        MenuEntity merged = (MenuEntity) RelationalMapper.merge(nu, og, entityManager);
        MenuEntity updated = repository.save(merged);
        return (MenuDto) RelationalMapper.mapToDto(updated);
    }

    @Override
    public MenuDto deleteMenu(MenuDto menuDto) {
        Optional<MenuEntity> og = repository.findById(menuDto.getId());
        if (og.isEmpty()) return null;
        repository.delete(og.get());
        return (MenuDto) RelationalMapper.mapToDto(og.get());
    }
}
