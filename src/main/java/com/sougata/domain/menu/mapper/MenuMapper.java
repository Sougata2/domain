package com.sougata.domain.menu.mapper;

import com.sougata.domain.mapper.CycleAvoidingMappingContext;
import com.sougata.domain.menu.dto.MenuDto;
import com.sougata.domain.menu.entity.MenuEntity;
import com.sougata.domain.role.mapper.RoleMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public abstract class MenuMapper {

    public MenuDto toDto(MenuEntity entity, @Context CycleAvoidingMappingContext context) {
        if (entity == null) return null;

        MenuDto existing = context.getMappedInstance(entity, MenuDto.class);
        if (existing != null) {
            return existing;
        }

        MenuDto dto = new MenuDto();
        context.storeMappedInstance(entity, dto);

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setUrl(entity.getUrl());

        if (entity.getSubMenus() != null) {
            dto.setSubMenus(
                    entity.getSubMenus().stream()
                            .map(sub -> toDto(sub, context))
                            .collect(java.util.stream.Collectors.toSet())
            );
        }

        // Optionally map roles if needed
        // dto.setRoles(...);

        return dto;
    }

    public MenuEntity toEntity(MenuDto dto, @Context CycleAvoidingMappingContext context) {
        if (dto == null) return null;

        MenuEntity existing = context.getMappedInstance(dto, MenuEntity.class);
        if (existing != null) {
            return existing;
        }

        MenuEntity entity = new MenuEntity();
        context.storeMappedInstance(dto, entity);

        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setUrl(dto.getUrl());

        if (dto.getSubMenus() != null) {
            entity.setSubMenus(
                    dto.getSubMenus().stream()
                            .map(sub -> toEntity(sub, context))
                            .collect(java.util.stream.Collectors.toSet())
            );
        }

        // Optionally map roles if needed
        // entity.setRoles(...);

        return entity;
    }
}


