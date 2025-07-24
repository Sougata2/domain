package com.sougata.domain.domain.specification.dto;

import com.sougata.domain.domain.activity.dto.ActivityDto;
import com.sougata.domain.domain.devices.dto.DeviceDto;
import com.sougata.domain.shared.MasterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link com.sougata.domain.domain.specification.entity.SpecificationEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationDto implements Serializable, MasterDto {
    private Long id;
    private String name;
    private Set<ActivityDto> activities;
    private Set<DeviceDto> devices;
    private Double price;
    private LocalDateTime createdAt;
}