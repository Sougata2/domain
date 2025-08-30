package com.sougata.domain.domain.activity.dto;

import com.sougata.domain.domain.devices.dto.DeviceDto;
import com.sougata.domain.domain.specification.dto.SpecificationDto;
import com.sougata.domain.domain.subService.dto.SubServiceDto;
import com.sougata.domain.shared.MasterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link com.sougata.domain.domain.activity.entity.ActivityEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDto implements Serializable, MasterDto {
    private Long id;
    private String name;
    private Set<SpecificationDto> specifications;
    private Set<DeviceDto> devices;
    private Set<SubServiceDto> subServices;
    private LocalDateTime createdAt;
}