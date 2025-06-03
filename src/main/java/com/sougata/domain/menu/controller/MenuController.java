package com.sougata.domain.menu.controller;

import com.sougata.domain.menu.dto.MenuDto;
import com.sougata.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MenuService service;

    @GetMapping
    public ResponseEntity<List<MenuDto>> getMenus(@RequestParam(name = "top-level", required = false) Integer topLevel) {
        logger.info("getMenus");
        try {
            if (topLevel == null) {
                return ResponseEntity.ok(service.findAllMenus());
            }
            return ResponseEntity.ok(service.findAllTopLevelMenus());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuDto> getMenuById(@PathVariable(name = "id") Long menuId) {
        logger.info("getMenuById : {}", menuId);
        try {
            MenuDto menuDto = service.findMenuById(menuId);
            if (menuDto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(menuDto);
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

    @DeleteMapping
    public ResponseEntity<MenuDto> deleteMenu(MenuDto dto) {
        logger.info("deleteMenu {}", dto);
        try {
            MenuDto deleted = service.deleteMenu(dto);
            if (deleted == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(deleted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
