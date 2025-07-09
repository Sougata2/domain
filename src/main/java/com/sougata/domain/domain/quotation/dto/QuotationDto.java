package com.sougata.domain.domain.quotation.dto;

import com.sougata.domain.domain.application.entity.ApplicationEntity;
import com.sougata.domain.shared.MasterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.sougata.domain.domain.quotation.entity.QuotationEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuotationDto implements Serializable, MasterDto {
    private Long id;
    private Double netPayableAmount;
    private Double discount;
    private Double tax;
    private ApplicationEntity application;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}