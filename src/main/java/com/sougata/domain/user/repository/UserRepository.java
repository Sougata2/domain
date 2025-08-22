package com.sougata.domain.user.repository;

import com.sougata.domain.role.entity.RoleEntity;
import com.sougata.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("select ue from UserEntity ue")
    List<UserEntity> findAllUsersWithRolesAndDefaultRole();

    @Query("select ue from UserEntity ue where ue.email = :email")
    Optional<UserEntity> findByEmail(String email);

    @Query("select re from UserEntity ue " +
            "left join ue.defaultRole re " +
            "where ue.id = :userId")
    RoleEntity findDefaultRoleForUser(Long userId);

    @Query("select e from UserEntity e where e.defaultRole.id = :defaultRoleId")
    Optional<UserEntity> findByDefaultRoleId(Long defaultRoleId);
}
