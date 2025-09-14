package com.sougata.domain.domain.job.dto;

import com.sougata.domain.domain.workFlowAction.dto.WorkFlowActionDto;
import com.sougata.domain.file.dto.FileDto;
import com.sougata.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobProcessDto {
    private JobDto job;
    private WorkFlowActionDto workFlowAction;
    private UserDto assignee;
    private UserDto assigner;
    private String comments;
    private FileDto file;
}
