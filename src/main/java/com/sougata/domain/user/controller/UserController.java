package com.sougata.domain.user.controller;

import com.sougata.domain.user.dto.UserDto;
import com.sougata.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
}
