package com.sougata.domain.domain.viewComponent.repository;

import com.sougata.domain.domain.viewComponent.entity.ViewComponentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewComponentRepository extends JpaRepository<ViewComponentEntity, Long> {
    @Query("select e from ViewComponentEntity e " +
            "join fetch RoleEntity f " +
            "where f.id = :roleId and e.applicationType = :applicationType")
    List<ViewComponentEntity> findAllByRoleIdAndApplicationType(Long roleId, String applicationType);
}