package com.sougata.domain.domain.devices.dto;

import com.sougata.domain.domain.activity.dto.ActivityDto;
import com.sougata.domain.domain.application.dto.ApplicationDto;
import com.sougata.domain.domain.specification.dto.SpecificationDto;
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
    private String heightUnit;
    private Long weight;
    private String weightUnit;
    private Long length;
    private Long quantity;
    private String lengthUnit;
    private Set<ActivityDto> activities;
    private Set<SpecificationDto> specifications;
    private ApplicationDto application;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}