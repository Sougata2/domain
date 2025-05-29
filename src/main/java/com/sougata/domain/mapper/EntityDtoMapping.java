package com.sougata.domain.mapper;

import com.sougata.domain.menu.dto.MenuDto;
import com.sougata.domain.menu.entity.MenuEntity;
import com.sougata.domain.role.dto.RoleDto;
import com.sougata.domain.role.entity.RoleEntity;
import com.sougata.domain.shared.MasterDto;
import com.sougata.domain.shared.MasterEntity;
import com.sougata.domain.user.dto.UserDto;
import com.sougata.domain.user.entity.UserEntity;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Component
public class EntityDtoMapping {
    private final Map<Class<? extends MasterDto>, Class<? extends MasterEntity>> dtoToEntityMap;
    private final Map<Class<? extends MasterEntity>, Class<? extends MasterDto>> entityToDtoMap;

    public EntityDtoMapping() {
        entityToDtoMap = Map.ofEntries(
                Map.entry(MenuEntity.class, MenuDto.class),
                Map.entry(RoleEntity.class, RoleDto.class),
                Map.entry(UserEntity.class, UserDto.class)
        );

        dtoToEntityMap = Map.ofEntries(
                Map.entry(MenuDto.class, MenuEntity.class),
                Map.entry(RoleDto.class, RoleEntity.class),
                Map.entry(UserDto.class, UserEntity.class)
        );
    }
}
