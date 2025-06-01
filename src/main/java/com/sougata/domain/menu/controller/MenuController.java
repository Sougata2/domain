package com.sougata.domain.menu.controller;

import com.sougata.domain.menu.dto.MenuDto;
import com.sougata.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MenuService service;

    @GetMapping("/top-level")
    public ResponseEntity<List<MenuDto>> getTopLevelMenus() {
        logger.info("getTopLevelMenus");
        try {
            return ResponseEntity.ok(service.findAllTopLevelMenus());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity<MenuDto> createMenu(@RequestBody MenuDto dto) {
        logger.info("createMenu : {}", dto);
        try {
            MenuDto created = service.createMenu(dto);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping
    public ResponseEntity<MenuDto> updateMenu(@RequestBody MenuDto menuDto) {
        logger.info("updateMenu {}", menuDto);
        try {
            MenuDto updated = service.updateMenu(menuDto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
