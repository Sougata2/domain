package com.sougata.domain.menu.repository;

import com.sougata.domain.menu.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Long> {

    /**
     * {@code @EntityGraph(attributePaths = {
     * "subMenus",
     * "subMenus.subMenus",
     * "subMenus.subMenus.subMenus"
     * })}
     * <p>
     * NOTE: entity graph will load the submenus up to 3 level eagerly
     * but when the mapper touches the submenus recursively, all the submenus
     * will be queried.
     * so here entity graph does not make any difference, because in the end,
     * all the relations are being fetched.
     * </p>
     */

    @Query("select me from MenuEntity me where me.menu is null")
    List<MenuEntity> findAllMenusWithSubMenus();

    @Query("select e from MenuEntity e where e.url != ''")
    List<MenuEntity> findAllSubMenus();
}
