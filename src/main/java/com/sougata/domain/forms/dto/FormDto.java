package com.sougata.domain.forms.dto;

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
 * DTO for {@link com.sougata.domain.forms.entity.FormEntity}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormDto implements Serializable, MasterDto {
    private Long id;
    private String name;
    private Set<SubServiceDto> subServices;
    private LocalDateTime createdAt;
}