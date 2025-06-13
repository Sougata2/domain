package com.sougata.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class AuthDto {
    private Long id;
    private String username;
    private String password;
    private String token;
    private String expiration;
}
