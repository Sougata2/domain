package com.sougata.domain.services.dto;

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
 * DTO for {@link com.sougata.domain.services.entity.ServiceEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDto implements Serializable, MasterDto {
    private Long id;
    private String name;
    private Set<SubServiceDto> subServices;
    private LocalDateTime createdAt;
}