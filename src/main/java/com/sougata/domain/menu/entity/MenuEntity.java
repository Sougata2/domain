package com.sougata.domain.menu.entity;


import com.sougata.domain.role.entity.RoleEntity;
import com.sougata.domain.shared.MasterEntity;
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
@Table(name = "menus")
public class MenuEntity implements MasterEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String url;

    @OneToMany(mappedBy = "menu", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    private Set<MenuEntity> subMenus;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    private MenuEntity menu;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "role_menu_map", joinColumns = @JoinColumn(name = "menu_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;


    @Override
    public String toString() {
        return "MenuEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", subMenus=" + (subMenus != null
                ? subMenus.stream()
                .map(m -> m != null ? m.getName() : "null")
                .toList()
                : "null") +
                ", menu=" + (menu != null ? menu.getName() : "null") +
                ", roles=" + (roles != null
                ? roles.stream()
                .map(r -> r != null ? r.getName() : "null")
                .toList()
                : "null") +
                '}';
    }

}
