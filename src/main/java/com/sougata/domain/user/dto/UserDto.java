package com.sougata.domain.user.dto;

import com.sougata.domain.role.dto.RoleDto;
import com.sougata.domain.shared.MasterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * DTO for {@link com.sougata.domain.user.entity.UserEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements MasterDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<RoleDto> roles;
    private RoleDto defaultRole;

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + (roles != null ? roles.stream()
                .map(role -> role != null ? role.getName() : "null")
                .toList() : "null") +
                ", defaultRole=" + (defaultRole != null ? defaultRole.getName() : "null") +
                '}';
    }

}