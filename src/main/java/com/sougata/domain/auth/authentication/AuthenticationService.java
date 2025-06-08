package com.sougata.domain.auth.authentication;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    UserDetails authenticate(String username, String password);

    String generateToken(String username);

    UserDetails validateToken(String token);
}
