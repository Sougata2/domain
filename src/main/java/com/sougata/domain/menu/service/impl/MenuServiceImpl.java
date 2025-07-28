package com.sougata.domain.menu.service.impl;

import com.sougata.domain.domain.formStages.entity.FormStageEntity;
import com.sougata.domain.mapper.RelationalMapper;
import com.sougata.domain.menu.dto.MenuDto;
import com.sougata.domain.menu.entity.MenuEntity;
import com.sougata.domain.menu.repository.MenuRepository;
import com.sougata.domain.menu.service.MenuService;
import com.sougata.domain.role.entity.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final RelationalMapper mapper;
    private final MenuRepository repository;

    @Override
    public List<MenuDto> findAllTopLevelMenus() {
        List<MenuEntity> entities = repository.findAllMenusWithSubMenus();
        return entities.stream().map(e -> (MenuDto) mapper.mapToDto(e)).toList();
    }

    @Override
    public List<MenuDto> findAllSubMenus() {
        List<MenuEntity> entities = repository.findAllSubMenus();
        return entities.stream().map(e -> (MenuDto) mapper.mapToDto(e)).toList();
    }

    @Override
    public List<MenuDto> findAllMenus() {
        List<MenuEntity> entities = repository.findAll();
        return entities.stream().map(e -> (MenuDto) mapper.mapToDto(e)).toList();
    }

    @Override
    @Transactional
    public MenuDto createMenu(MenuDto menuDto) {
        MenuEntity entity = (MenuEntity) mapper.mapToEntity(menuDto);
        MenuEntity saved = repository.save(entity);
        return (MenuDto) mapper.mapToDto(saved);
    }

    @Override
    @Transactional
    public MenuDto updateMenu(MenuDto menuDto) {
        MenuEntity og = repository.findById(menuDto.getId()).orElse(null);
        if (og == null) return null;
        MenuEntity nu = (MenuEntity) mapper.mapToEntity(menuDto);
        MenuEntity merged = (MenuEntity) mapper.merge(nu, og);
        MenuEntity updated = repository.save(merged);
        return (MenuDto) mapper.mapToDto(updated);
    }

    @Override
    @Transactional
    public List<MenuDto> bulkUpdateMenus(List<MenuDto> dtos) {
        Map<MenuEntity, MenuEntity> map = new HashMap<>();
        dtos.forEach(d -> {
            MenuEntity nu = (MenuEntity) mapper.mapToEntity(d);
            MenuEntity og = repository.findById(d.getId()).orElseThrow();
            map.put(nu, og);
        });
        List<MenuEntity> mergedList = new ArrayList<>();
        for (Map.Entry<MenuEntity, MenuEntity> entry : map.entrySet()) {
            MenuEntity merged = (MenuEntity) mapper.merge(entry.getKey(), entry.getValue());
            mergedList.add(merged);
        }
        List<MenuEntity> updatedList = repository.saveAll(mergedList);
        return updatedList.stream().map(e -> (MenuDto) mapper.mapToDto(e)).toList();
    }

    @Override
    @Transactional
    public MenuDto deleteMenu(MenuDto menuDto) {
        Optional<MenuEntity> og = repository.findById(menuDto.getId());
        if (og.isEmpty()) return null;
        //detach relations
        if (!og.get().getRoles().isEmpty()) {
            for (RoleEntity role : og.get().getRoles()) {
                role.getMenus().remove(og.get());
            }
            og.get().setRoles(new HashSet<>());
        }

        if (og.get().getMenu() != null) {
            for (MenuEntity submenu : og.get().getMenu().getSubMenus()) {
                if (submenu.getMenu().getId().equals(og.get().getId())) {
                    submenu.setMenu(null);
                }
            }
            og.get().setMenu(null);
        }

        if (!og.get().getStages().isEmpty()) {
            for (FormStageEntity stage : og.get().getStages()) {
                if (stage.getMenu().getId().equals(og.get().getId())) {
                    stage.setMenu(null);
                }
            }
            og.get().setStages(new HashSet<>());
        }

        repository.delete(og.get());
        return (MenuDto) mapper.mapToDto(og.get());
    }

    @Override
    public MenuDto findMenuById(Long menuId) {
        Optional<MenuEntity> og = repository.findById(menuId);
        return og.map(e -> (MenuDto) mapper.mapToDto(e)).orElse(null);
    }
}
