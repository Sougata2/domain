package com.sougata.domain.auth.userDetails;

import com.sougata.domain.exceptions.DefaultRoleNotFoundException;
import com.sougata.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class AppUserDetails implements UserDetails {
    private final UserEntity user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // return user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
        if (user.getDefaultRole() == null) {
            throw new DefaultRoleNotFoundException("No Default Role found this user.");
        }
        return List.of(new SimpleGrantedAuthority(user.getDefaultRole().getName()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    public Long getId() {
        return user.getId();
    }

    public String getName() {
        return "%s %s".formatted(user.getFirstName(), user.getLastName());
    }
}
