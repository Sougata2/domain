package com.sougata.domain.role.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sougata.domain.menu.dto.MenuDto;
import com.sougata.domain.shared.MasterDto;
import com.sougata.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.sougata.domain.role.entity.RoleEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto implements Serializable, MasterDto {
    private Long id;
    private String name;
    private Set<MenuDto> menus;
    @JsonIgnore
    private Set<UserDto> users;
    @JsonIgnore
    private Set<UserDto> defaultRoleUsers;

    @Override
    public String toString() {
        return "RoleDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", menus=" + (menus != null
                ? menus.stream()
                .map(m -> m != null ? m.getName() : "null")
                .toList()
                : "null") +
                ", users=" + (users != null
                ? users.stream()
                .map(u -> u != null ? u.getEmail() : "null")
                .toList()
                : "null") +
                ", defaultRoleUsers=" + (defaultRoleUsers != null
                ? defaultRoleUsers.stream()
                .map(u -> u != null ? u.getEmail() : "null")
                .toList()
                : "null") +
                '}';
    }

}