package com.sougata.domain.role.entity;

import com.sougata.domain.menu.entity.MenuEntity;
import com.sougata.domain.shared.MasterEntity;
import com.sougata.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class RoleEntity implements MasterEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "role_menu_map", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "menu_id"))
    private Set<MenuEntity> menus;

    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<UserEntity> users;

    @OneToMany(mappedBy = "defaultRole", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    private Set<UserEntity> defaultRoleUsers;


    @Override
    public String toString() {
        return "RoleEntity{" +
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

    @PrePersist
    protected void onCreate() {
        if (!name.startsWith("ROLE_")) {
            name = "ROLE_" + name.toUpperCase();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        if (!name.startsWith("ROLE_")) {
            name = "ROLE_" + name.toUpperCase();
        }
    }

    @PreRemove
    protected void onDelete() {
        menus.clear();
        users.clear();
        for (UserEntity user : defaultRoleUsers) {
            user.setDefaultRole(null);
        }
    }

}
