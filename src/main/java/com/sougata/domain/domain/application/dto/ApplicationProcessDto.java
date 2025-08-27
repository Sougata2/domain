package com.sougata.domain.domain.application.dto;

import com.sougata.domain.domain.workFlowAction.dto.WorkFlowActionDto;
import com.sougata.domain.user.dto.UserDto;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationProcessDto {
    private ApplicationDto application;
    private WorkFlowActionDto workFlowAction;
    private UserDto assignee;
    private UserDto assigner;
    private String comments;
}
