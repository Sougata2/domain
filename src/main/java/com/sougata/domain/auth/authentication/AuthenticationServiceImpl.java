package com.sougata.domain.auth.authentication;

import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public UserDetails authenticate(String username, String password) {
        return null;
    }

    @Override
    public String generateToken(String username) {
        return "";
    }

    @Override
    public UserDetails validateToken(String token) {
        return null;
    }
}
