package com.sougata.domain.auth.controller;

import com.sougata.domain.auth.authentication.AuthenticationService;
import com.sougata.domain.auth.dto.AuthDto;
import com.sougata.domain.auth.jwt.JwtProperties;
import com.sougata.domain.auth.userDetails.AppUserDetails;
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
                .name(((AppUserDetails) authenticatedUser).getName())
                .id(((AppUserDetails) authenticatedUser).getId())
                .token(token)
                .expiration(jwtProperties.getExpiry())
                .build());
    }

    @PostMapping("/validate-token")
    public ResponseEntity<AuthDto> validateToken(@RequestBody AuthDto dto) {
        UserDetails authenticatedUser = service.validateToken(dto.getToken());
        return ResponseEntity.ok(AuthDto.builder()
                .id(((AppUserDetails) authenticatedUser).getId())
                .name(((AppUserDetails) authenticatedUser).getName())
                .username(authenticatedUser.getUsername())
                .token(dto.getToken())
                .expiration(jwtProperties.getExpiry())
                .build());
    }
}
