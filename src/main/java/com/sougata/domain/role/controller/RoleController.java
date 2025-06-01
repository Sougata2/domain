package com.sougata.domain.role.controller;

import com.sougata.domain.role.dto.RoleDto;
import com.sougata.domain.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto dto) {
        logger.info("createRole : {}", dto);
        try {
            RoleDto created = service.createRole(dto);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping
    public ResponseEntity<RoleDto> updateRole(@RequestBody RoleDto dto) {
        logger.info("updateRole : {}", dto);
        try {
            RoleDto roleDto = service.updateRole(dto);
            return ResponseEntity.ok(roleDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping
    public ResponseEntity<RoleDto> deleteRole(@RequestBody RoleDto dto) {
        logger.info("deleteRole : {}", dto);
        try {
            RoleDto deleted = service.deleteRole(dto);
            if (deleted == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(deleted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
