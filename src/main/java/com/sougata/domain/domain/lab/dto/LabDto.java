package com.sougata.domain.domain.lab.dto;

import com.sougata.domain.domain.application.dto.ApplicationDto;
import com.sougata.domain.shared.MasterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link com.sougata.domain.domain.lab.entity.LabEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LabDto implements Serializable, MasterDto {
    private Long id;
    private String name;
    private String address;
    private String email;
    private String phone;
    private Set<ApplicationDto> applications;
    private LocalDateTime createdAt;
}