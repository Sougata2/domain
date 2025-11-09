package com.sougata.domain.domain.viewComponent.repository;

import com.sougata.domain.domain.viewComponent.entity.ViewComponentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewComponentRepository extends JpaRepository<ViewComponentEntity, Long> {
    @Query("select e from ViewComponentEntity e " +
            "join fetch e.roles f " +
            "join fetch e.statuses g " +
            "where f.id = :roleId and g.id = :statusId and e.applicationType = :applicationType")
    List<ViewComponentEntity> findAllByRoleIdStatusIdAndApplicationType(Long roleId, Long statusId, String applicationType);
}