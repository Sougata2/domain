package com.sougata.domain.domain.workFlowAction.controller;

import com.sougata.domain.domain.application.dto.ApplicationDto;
import com.sougata.domain.domain.application.service.ApplicationService;
import com.sougata.domain.domain.status.dto.StatusDto;
import com.sougata.domain.domain.workFlowAction.dto.WorkFlowActionDto;
import com.sougata.domain.domain.workFlowAction.service.WorkFlowActionService;
import com.sougata.domain.domain.workflowHistory.service.WorkFlowHistoryService;
import com.sougata.domain.user.dto.UserDto;
import com.sougata.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workflow-action")
public class WorkFlowActionController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final WorkFlowHistoryService workFlowHistoryService;
    private final ApplicationService applicationService;
    private final WorkFlowActionService service;
    private final UserService userService;

    @GetMapping("/by-status/{id}")
    public ResponseEntity<List<WorkFlowActionDto>> findByStatusId(@PathVariable(value = "id") Long statusId) {
        logger.info("workFlowAction.findByStatusId : {}", statusId);
        return ResponseEntity.ok(service.findByStatusId(statusId));
    }

    @GetMapping("/by-reference-number/{number}")
    public ResponseEntity<List<WorkFlowActionDto>> findByReferenceNumber(@PathVariable(value = "number") String referenceNumber) {
        logger.info("workFlowAction.findByReferenceNumber : {}", referenceNumber);
        ApplicationDto application = applicationService.findByReferenceNumber(referenceNumber);
        return ResponseEntity.ok(service.findByStatusId(application.getStatus().getId()));
    }

    @GetMapping("/assignee-list-for-action/{actionId}/{referenceNumber}")
    public ResponseEntity<List<UserDto>> findAssigneeForAction(
            @PathVariable(value = "actionId") Long workFlowActionId,
            @PathVariable(value = "referenceNumber") String referenceNumber,
            @RequestParam(required = false, value = "regressive") Boolean regressive
    ) {
        WorkFlowActionDto action = service.findById(workFlowActionId);
        if (regressive) {
            logger.info("workFlowAction.findAssigneeForAction [regressive] workFlowActionId : {}, referenceNumber: {}", workFlowActionId, referenceNumber);
            return ResponseEntity.ok(workFlowHistoryService.getRegressiveUser(referenceNumber, action.getTargetRole().getId()));
        }
        logger.info("workFlowAction.findAssigneeForAction : {}", workFlowActionId);
        ApplicationDto application = applicationService.findByReferenceNumber(referenceNumber);
        return ResponseEntity.ok(userService.findByDefaultRoleIdAndLabId(action.getTargetRole().getId(), application.getLab().getId()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<WorkFlowActionDto>> findAll() {
        logger.info("workFlowAction.findAll");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkFlowActionDto> findById(@PathVariable(value = "id") Long actionId) {
        logger.info("workFlowAction.findById : {}", actionId);
        return ResponseEntity.ok(service.findById(actionId));
    }

    @GetMapping("/find-target-status/{id}")
    public ResponseEntity<List<StatusDto>> findTargetStatusByCurrentStatus(@PathVariable(value = "id") Long statusId) {
        logger.info("workFlowAction.findTargetStatusByCurrentStatus : {}", statusId);
        return ResponseEntity.ok(service.findTargetStatusByCurrentStatus(statusId));
    }

    @PostMapping("/search")
    public ResponseEntity<Page<WorkFlowActionDto>> search(@RequestBody Map<String, String> filter, Pageable pageable) {
        logger.info("workFlowAction.search : {}, {}", filter, pageable);
        return ResponseEntity.ok(service.search(filter, pageable));
    }

    @PostMapping
    public ResponseEntity<WorkFlowActionDto> create(@RequestBody WorkFlowActionDto dto) {
        logger.info("workFlowAction.create : {}", dto);
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping
    public ResponseEntity<WorkFlowActionDto> update(@RequestBody WorkFlowActionDto dto) {
        logger.info("workFlowAction.update : {}", dto);
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<WorkFlowActionDto> delete(@RequestBody WorkFlowActionDto dto) {
        logger.info("workFlowAction.delete : {}", dto);
        return ResponseEntity.ok(service.delete(dto));
    }
}
