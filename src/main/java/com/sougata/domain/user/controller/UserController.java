package com.sougata.domain.user.controller;

import com.sougata.domain.role.dto.RoleDto;
import com.sougata.domain.user.dto.UserDto;
import com.sougata.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService service;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        logger.info("getAllUsers");
        try {
            List<UserDto> users = service.findAllUsersWithRolesAndDefaultRole();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        logger.info("getUserById id: {}", id);
        try {
            UserDto user = service.findUserById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/default-role/{id}")
    public ResponseEntity<RoleDto> getDefaultRoleForUser(@PathVariable Long id) {
        logger.info("getDefaultRoleForUser id: {}", id);
        try {
            RoleDto defaultRole = service.getDefaultRoleForUser(id);
            if (defaultRole == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(defaultRole);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto) {
        logger.info("createUser : {}", dto);
        try {
            UserDto user = service.createUser(dto);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto dto) {
        logger.info("updateUser : {}", dto);
        try {
            UserDto user = service.updateUser(dto);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping
    public ResponseEntity<UserDto> deleteUser(@RequestBody UserDto dto) {
        logger.info("deleteUser : {}", dto);
        try {
            UserDto deleted = service.deleteUser(dto);
            if (deleted == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(deleted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
