package com.sougata.domain.subService.dto;

import com.sougata.domain.domain.forms.dto.FormDto;
import com.sougata.domain.domain.services.dto.ServiceDto;
import com.sougata.domain.shared.MasterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link com.sougata.domain.subService.entity.SubServiceEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubServiceDto implements Serializable, MasterDto {
    private Long id;
    private String name;
    private Set<ServiceDto> services;
    private FormDto form;
    private LocalDateTime createdAt;
}