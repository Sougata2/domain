package com.sougata.domain.domain.status.entity;

import com.sougata.domain.domain.application.entity.ApplicationEntity;
import com.sougata.domain.domain.job.entity.JobEntity;
import com.sougata.domain.domain.jobWorkFlowHistory.entity.JobWorkFlowHistoryEntity;
import com.sougata.domain.domain.status.enums.WorkFlowActionType;
import com.sougata.domain.domain.workFlowAction.entity.WorkFlowActionEntity;
import com.sougata.domain.domain.workflowHistory.entity.WorkFlowHistoryEntity;
import com.sougata.domain.shared.MasterEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "statuses")
public class StatusEntity implements MasterEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String postDescription;

    @Enumerated(EnumType.STRING)
    private WorkFlowActionType actionType;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}, mappedBy = "status")
    private Set<ApplicationEntity> applications;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}, mappedBy = "status")
    private Set<WorkFlowHistoryEntity> workFlowHistory;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "status")
    private Set<WorkFlowActionEntity> actions;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}, mappedBy = "targetStatus")
    private Set<WorkFlowActionEntity> targetStatusActions;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}, mappedBy = "status")
    private Set<JobEntity> jobs;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}, mappedBy = "status")
    private Set<JobWorkFlowHistoryEntity> jobWorkFlowHistory;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "StatusEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", applications=" + (applications != null
                ? applications.stream()
                .map(app -> app != null ? app.getId() : "null")
                .toList()
                : "null") +
                ", createdAt=" + createdAt +
                '}';
    }
}
