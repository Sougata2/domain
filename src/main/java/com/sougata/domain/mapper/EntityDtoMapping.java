package com.sougata.domain.mapper;

import com.sougata.domain.forms.dto.FormDto;
import com.sougata.domain.forms.entity.FormEntity;
import com.sougata.domain.menu.dto.MenuDto;
import com.sougata.domain.menu.entity.MenuEntity;
import com.sougata.domain.role.dto.RoleDto;
import com.sougata.domain.role.entity.RoleEntity;
import com.sougata.domain.services.dto.ServiceDto;
import com.sougata.domain.services.entity.ServiceEntity;
import com.sougata.domain.shared.MasterDto;
import com.sougata.domain.shared.MasterEntity;
import com.sougata.domain.subService.dto.SubServiceDto;
import com.sougata.domain.subService.entity.SubServiceEntity;
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
                Map.entry(UserEntity.class, UserDto.class),
                Map.entry(ServiceEntity.class, ServiceDto.class),
                Map.entry(SubServiceEntity.class, SubServiceDto.class),
                Map.entry(FormEntity.class, FormDto.class)
        );

        dtoToEntityMap = Map.ofEntries(
                Map.entry(MenuDto.class, MenuEntity.class),
                Map.entry(RoleDto.class, RoleEntity.class),
                Map.entry(UserDto.class, UserEntity.class),
                Map.entry(ServiceDto.class, ServiceEntity.class),
                Map.entry(SubServiceDto.class, SubServiceEntity.class),
                Map.entry(FormDto.class, FormEntity.class)
        );
    }
}
