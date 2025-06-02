package com.sougata.domain.user.repository;

import com.sougata.domain.role.entity.RoleEntity;
import com.sougata.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("select ue from UserEntity ue")
    List<UserEntity> findAllUsersWithRolesAndDefaultRole();

    @Query("select re from UserEntity ue " +
            "left join ue.defaultRole re " +
            "where ue.id = :userId")
    RoleEntity findDefaultRoleForUser(Long userId);
}
