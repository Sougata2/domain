package com.sougata.domain.domain.services.dto;

import com.sougata.domain.domain.application.dto.ApplicationDto;
import com.sougata.domain.domain.services.entity.ServiceEntity;
import com.sougata.domain.shared.MasterDto;
import com.sougata.domain.subService.dto.SubServiceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link ServiceEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDto implements Serializable, MasterDto {
    private Long id;
    private String name;
    private Set<SubServiceDto> subServices;
    private Set<ApplicationDto> applications;
    private LocalDateTime createdAt;
}