package com.sougata.domain.domain.job.dto;

import com.sougata.domain.domain.devices.dto.DeviceDto;
import com.sougata.domain.domain.jobWorkFlowHistory.dto.JobWorkFlowHistoryDto;
import com.sougata.domain.domain.lab.dto.LabDto;
import com.sougata.domain.domain.labTestRecord.dto.LabTestRecordDto;
import com.sougata.domain.domain.status.dto.StatusDto;
import com.sougata.domain.shared.MasterDto;
import com.sougata.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link com.sougata.domain.domain.job.entity.JobEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobDto implements Serializable, MasterDto {
    private Long id;
    private DeviceDto device;
    private StatusDto status;
    private UserDto assignee;
    private LabDto lab;
    private Set<JobWorkFlowHistoryDto> workFlowHistory;
    private Set<LabTestRecordDto> testRecords;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}