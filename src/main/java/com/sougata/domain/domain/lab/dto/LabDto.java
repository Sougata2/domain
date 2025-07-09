package com.sougata.domain.domain.lab.dto;

import com.sougata.domain.domain.application.entity.ApplicationEntity;
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
    private Set<ApplicationEntity> applications;
    private LocalDateTime createdAt;
}