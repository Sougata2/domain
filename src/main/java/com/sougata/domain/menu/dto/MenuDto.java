package com.sougata.domain.menu.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sougata.domain.role.dto.RoleDto;
import com.sougata.domain.shared.MasterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.sougata.domain.menu.entity.MenuEntity}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto implements Serializable, MasterDto {
    private Long id;
    private String name;
    private String url;
    private Set<MenuDto> subMenus;
    private MenuDto menu;
    @JsonIgnore
    private Set<RoleDto> roles;

    @Override
    public String toString() {
        return "MenuDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", subMenus=" + (subMenus != null
                ? subMenus.stream().map(m -> m != null ? m.getName() : "null").toList()
                : "null") +
                ", menu=" + (menu != null ? menu.getName() : "null") +
                ", roles=" + (roles != null
                ? roles.stream().map(r -> r != null ? r.getName() : "null").toList()
                : "null") +
                '}';
    }

}