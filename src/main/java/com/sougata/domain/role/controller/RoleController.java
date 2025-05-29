package com.sougata.domain.role.controller;

import com.sougata.domain.role.dto.RoleDto;
import com.sougata.domain.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RoleService service;

    @GetMapping
    public ResponseEntity<List<RoleDto>> getRoles() {
        logger.info("getRoles");
        try {
            List<RoleDto> roles = service.getAllRoles();
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
