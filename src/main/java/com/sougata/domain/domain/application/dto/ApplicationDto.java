package com.sougata.domain.domain.application.dto;

import com.sougata.domain.domain.devices.dto.DeviceDto;
import com.sougata.domain.domain.document.dto.DocumentDto;
import com.sougata.domain.domain.lab.dto.LabDto;
import com.sougata.domain.domain.quotation.dto.QuotationDto;
import com.sougata.domain.domain.services.dto.ServiceDto;
import com.sougata.domain.domain.status.dto.StatusDto;
import com.sougata.domain.shared.MasterDto;
import com.sougata.domain.subService.dto.SubServiceDto;
import com.sougata.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link com.sougata.domain.domain.application.entity.ApplicationEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDto implements Serializable, MasterDto {
    private Long id;
    private String referenceNumber;
    private UserDto applicant;
    private ServiceDto service;
    private SubServiceDto subService;
    private LabDto lab;
    private Set<DeviceDto> devices;
    private Set<DocumentDto> documents;
    private QuotationDto quotation;
    private StatusDto status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @Override
    public String toString() {
        return "ApplicationDto{" +
                "id=" + id +
                ", referenceNumber='" + referenceNumber + '\'' +
                ", applicant=" + (applicant != null ? applicant.getFirstName() + " " + applicant.getLastName() : "null") +
                ", service=" + (service != null ? service.getName() : "null") +
                ", subService=" + (subService != null ? subService.getName() : "null") +
                ", lab=" + (lab != null ? lab.getName() : "null") +
                ", devices=" + (devices != null ? devices.stream().map(d -> d != null ? d.getName() : "null").toList() : "null") +
                ", documents=" + (documents != null ? documents.stream().map(d -> d != null ? d.getName() : "null").toList() : "null") +
                ", quotation=" + (quotation != null ? quotation.toString() : "null") +
                ", status=" + (status != null ? status.getName() : "null") +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}