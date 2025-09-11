package com.sougata.domain.user.service.impl;

import com.sougata.domain.domain.application.entity.ApplicationEntity;
import com.sougata.domain.domain.job.entity.JobEntity;
import com.sougata.domain.domain.lab.entity.LabEntity;
import com.sougata.domain.domain.lab.repository.LabRepository;
import com.sougata.domain.domain.workflowHistory.entity.WorkFlowHistoryEntity;
import com.sougata.domain.mapper.RelationalMapper;
import com.sougata.domain.role.dto.RoleDto;
import com.sougata.domain.role.entity.RoleEntity;
import com.sougata.domain.role.repository.RoleRepository;
import com.sougata.domain.user.dto.UserDto;
import com.sougata.domain.user.entity.UserEntity;
import com.sougata.domain.user.repository.UserRepository;
import com.sougata.domain.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RelationalMapper mapper;
    private final UserRepository repository;
    private final LabRepository labRepository;
    private final RoleRepository roleRepository;

    @Override
    public List<UserDto> findAllUsersWithRolesAndDefaultRole() {
        List<UserEntity> entities = repository.findAllUsersWithRolesAndDefaultRole();
        return entities.stream().map(e -> (UserDto) mapper.mapToDto(e)).toList();
    }

    @Override
    public List<UserDto> findByDefaultRoleIdAndLabId(Long defaultRoleId, Long labId) {
        try {
            Optional<RoleEntity> role = roleRepository.findById(defaultRoleId);
            if (role.isEmpty()) {
                throw new EntityNotFoundException("Role Entity with Id %d not found".formatted(defaultRoleId));
            }
            Optional<LabEntity> lab = labRepository.findById(labId);
            if (lab.isEmpty()) {
                throw new EntityNotFoundException("Lab Entity with Id %d not found".formatted(labId));
            }

            List<UserEntity> entities = repository.findByDefaultRoleIdAndLabId(role.get().getId(), lab.get().getId());

            return entities.stream().map(e -> (UserDto) mapper.mapToDto(e)).toList();
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDto removeRole(Long userId, Long roleId) {
        try {
            Optional<UserEntity> user = repository.findById(userId);
            if (user.isEmpty()) {
                throw new EntityNotFoundException("User with Id %d not found".formatted(userId));
            }
            Optional<RoleEntity> role = roleRepository.findById(roleId);
            if (role.isEmpty()) {
                throw new EntityNotFoundException("Role Entity with Id %d not found".formatted(roleId));
            }

            user.get().getRoles().remove(role.get());
            UserEntity saved = repository.save(user.get());
            return (UserDto) mapper.mapToDto(saved);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDto createUser(UserDto dto) {
        UserEntity entity = (UserEntity) mapper.mapToEntity(dto);
        UserEntity created = repository.save(entity);
        return (UserDto) mapper.mapToDto(created);
    }

    @Override
    public UserDto updateUser(UserDto dto) {
        Optional<UserEntity> og = repository.findById(dto.getId());
        if (og.isEmpty()) {
            return null;
        }
        UserEntity nu = (UserEntity) mapper.mapToEntity(dto);
        System.out.println("*************************");
        System.out.println("og = " + og.get());
        System.out.println("nu = " + nu);
        UserEntity merged = (UserEntity) mapper.merge(nu, og.get());
        System.out.println("merged = " + merged);
        System.out.println("*************************");

        UserEntity updated = repository.save(merged);
        return (UserDto) mapper.mapToDto(updated);
    }

    @Override
    public UserDto findUserById(Long id) {
        UserEntity entity = repository.findById(id).orElse(null);
        return (UserDto) mapper.mapToDto(entity);
    }

    @Override
    @Transactional
    public UserDto deleteUser(UserDto dto) {
        Optional<UserEntity> og = repository.findById(dto.getId());
        if (og.isEmpty()) {
            return null;
        }

        // detaching relations
        UserEntity user = og.get();
        for (RoleEntity role : user.getRoles()) {
            role.getUsers().remove(user);
        }
        user.setRoles(new HashSet<>());

        if (user.getDefaultRole() != null) {
            user.getDefaultRole().getDefaultRoleUsers().remove(user);
            user.setDefaultRole(null);
        }

        if (!user.getApplications().isEmpty()) {
            for (ApplicationEntity application : user.getApplications()) {
                application.setApplicant(null);
                application.setAssignee(null);
            }
            user.setApplications(new HashSet<>());
        }

        if (!user.getAssignments().isEmpty()) {
            for (ApplicationEntity application : user.getAssignments()) {
                if (application.getAssignee().getId().equals(user.getId())) {
                    application.setAssignee(null);
                }
            }
            user.setAssignments(new HashSet<>());
        }

        if (!user.getWorkFlowHistoryForAssignee().isEmpty()) {
            for (WorkFlowHistoryEntity workFlow : user.getWorkFlowHistoryForAssignee()) {
                if (workFlow.getAssignee().getId().equals(user.getId())) {
                    workFlow.setAssignee(null);
                }
            }
            user.setWorkFlowHistoryForAssignee(new HashSet<>());
        }

        if (!user.getWorkFlowHistoryForAssigner().isEmpty()) {
            for (WorkFlowHistoryEntity workFlow : user.getWorkFlowHistoryForAssigner()) {
                if (workFlow.getAssigner().getId().equals(user.getId())) {
                    workFlow.setAssigner(null);
                }
            }
            user.setWorkFlowHistoryForAssigner(new HashSet<>());
        }

        if (user.getLab() != null) {
            user.getLab().getUsers().remove(user);
            user.setLab(null);
        }

        if (!user.getJobs().isEmpty()) {
            for (JobEntity job : user.getJobs()) {
                job.setAssignee(null);
            }
        }


        UserDto result = (UserDto) mapper.mapToDto(user);
        repository.delete(user);
        return result;
    }

    @Override
    public RoleDto getDefaultRoleForUser(Long userId) {
        Optional<UserEntity> og = repository.findById(userId);
        if (og.isEmpty()) return null;
        RoleEntity defaultRole = repository.findDefaultRoleForUser(userId);
        return (RoleDto) mapper.mapToDto(defaultRole);
    }
}
