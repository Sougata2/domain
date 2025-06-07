package com.sougata.domain.menu.service.impl;

import com.sougata.domain.mapper.RelationalMapper;
import com.sougata.domain.menu.dto.MenuDto;
import com.sougata.domain.menu.entity.MenuEntity;
import com.sougata.domain.menu.repository.MenuRepository;
import com.sougata.domain.menu.service.MenuService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    public List<MenuDto> findAllMenus() {
        List<MenuEntity> entities = repository.findAll();
        return entities.stream().map(e -> (MenuDto) RelationalMapper.mapToDto(e)).toList();
    }

    @Override
    @Transactional
    public MenuDto createMenu(MenuDto menuDto) {
        MenuEntity entity = (MenuEntity) RelationalMapper.mapToEntity(menuDto);
        MenuEntity saved = repository.save(entity);
        return (MenuDto) RelationalMapper.mapToDto(saved);
    }

    @Override
    @Transactional
    public MenuDto updateMenu(MenuDto menuDto) {
        MenuEntity og = repository.findById(menuDto.getId()).orElse(null);
        if (og == null) return null;
        MenuEntity nu = (MenuEntity) RelationalMapper.mapToEntity(menuDto);
        MenuEntity merged = (MenuEntity) RelationalMapper.merge(nu, og, entityManager);
        MenuEntity updated = repository.save(merged);
        return (MenuDto) RelationalMapper.mapToDto(updated);
    }

    @Override
    @Transactional
    public List<MenuDto> bulkUpdateMenus(List<MenuDto> dtos) {
        Map<MenuEntity, MenuEntity> map = new HashMap<>();
        dtos.forEach(d -> {
            MenuEntity nu = (MenuEntity) RelationalMapper.mapToEntity(d);
            MenuEntity og = repository.findById(d.getId()).orElseThrow();
            map.put(nu, og);
        });
        List<MenuEntity> mergedList = new ArrayList<>();
        for (Map.Entry<MenuEntity, MenuEntity> entry : map.entrySet()) {
            MenuEntity merged = (MenuEntity) RelationalMapper.merge(entry.getKey(), entry.getValue(), entityManager);
            mergedList.add(merged);
        }
        List<MenuEntity> updatedList = repository.saveAll(mergedList);
        return updatedList.stream().map(e -> (MenuDto) RelationalMapper.mapToDto(e)).toList();
    }

    @Override
    @Transactional
    public MenuDto deleteMenu(MenuDto menuDto) {
        Optional<MenuEntity> og = repository.findById(menuDto.getId());
        if (og.isEmpty()) return null;
        repository.delete(og.get());
        return (MenuDto) RelationalMapper.mapToDto(og.get());
    }

    @Override
    public MenuDto findMenuById(Long menuId) {
        Optional<MenuEntity> og = repository.findById(menuId);
        return og.map(e -> (MenuDto) RelationalMapper.mapToDto(e)).orElse(null);
    }
}
