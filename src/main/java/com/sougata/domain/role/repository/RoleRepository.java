package com.sougata.domain.role.repository;

import com.sougata.domain.role.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    @Query("select re from RoleEntity re")
    List<RoleEntity> findAllRoles();

    @Query("select e from RoleEntity e where e.name = :role")
    Optional<RoleEntity> findByRoleName(String role);
}
