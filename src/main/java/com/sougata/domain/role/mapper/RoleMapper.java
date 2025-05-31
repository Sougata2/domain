package com.sougata.domain.role.mapper;

import com.sougata.domain.menu.mapper.MenuMapper;
import com.sougata.domain.role.dto.RoleDto;
import com.sougata.domain.role.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MenuMapper.class})
public interface RoleMapper {

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "defaultRoleUsers", ignore = true)
    RoleDto toDto(RoleEntity entity);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "defaultRoleUsers", ignore = true)
    RoleEntity toEntity(RoleDto dto);
}
