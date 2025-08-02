package com.sougata.domain.domain.devices.dto;

import com.sougata.domain.domain.activity.entity.ActivityEntity;
import com.sougata.domain.domain.application.entity.ApplicationEntity;
import com.sougata.domain.domain.specification.entity.SpecificationEntity;
import com.sougata.domain.shared.MasterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link com.sougata.domain.domain.devices.entity.DeviceEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDto implements Serializable, MasterDto {
    private Long id;
    private String name;
    private Long height;
    private Long weight;
    private Long length;
    private Set<ActivityEntity> activities;
    private Set<SpecificationEntity> specifications;
    private ApplicationEntity application;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}