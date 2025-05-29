package com.sougata.domain.user.service.impl;

import com.sougata.domain.mapper.RelationalMapper;
import com.sougata.domain.user.dto.UserDto;
import com.sougata.domain.user.entity.UserEntity;
import com.sougata.domain.user.repository.UserRepository;
import com.sougata.domain.user.service.UserService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final EntityManager entityManager;
    private final UserRepository repository;

    @Override
    public List<UserDto> findAllUsersWithRolesAndDefaultRole() {
        List<UserEntity> entities = repository.findAllUsersWithRolesAndDefaultRole();
        return entities.stream().map(e -> (UserDto) RelationalMapper.mapToDto(e)).toList();
    }

    @Override
    public UserDto createUser(UserDto dto) {
        UserEntity entity = (UserEntity) RelationalMapper.mapToEntity(dto);
        UserEntity created = repository.save(entity);
        return (UserDto) RelationalMapper.mapToDto(created);
    }

    @Override
    public UserDto updateUser(UserDto dto) {
        Optional<UserEntity> og = repository.findById(dto.getId());
        if (og.isEmpty()) {
            return null;
        }
        UserEntity nu = (UserEntity) RelationalMapper.mapToEntity(dto);
        UserEntity merged = (UserEntity) RelationalMapper.merge(nu, og.get(), entityManager);
        UserEntity updated = repository.save(merged);
        return (UserDto) RelationalMapper.mapToDto(updated);
    }

    @Override
    public UserDto findUserById(Long id) {
        UserEntity entity = repository.findById(id).orElse(null);
        return (UserDto) RelationalMapper.mapToDto(entity);
    }
}
