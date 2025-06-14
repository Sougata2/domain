package com.sougata.domain.auth.controller;

import com.sougata.domain.auth.authentication.AuthenticationService;
import com.sougata.domain.auth.dto.AuthDto;
import com.sougata.domain.auth.jwt.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService service;
    private final JwtProperties jwtProperties;

    @PostMapping("/login")
    public ResponseEntity<AuthDto> login(@RequestBody AuthDto dto) {
        UserDetails authenticatedUser = service.authenticate(dto.getUsername(), dto.getPassword());
        String token = service.generateToken(authenticatedUser);
        return ResponseEntity.ok(AuthDto.builder()
                .username(authenticatedUser.getUsername())
                .id(dto.getId())
                .token(token)
                .expiration(jwtProperties.getExpiry())
                .build());
    }
}
