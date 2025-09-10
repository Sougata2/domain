package com.sougata.domain.domain.jobWorkFlowHistory.entity;

import com.sougata.domain.domain.job.entity.JobEntity;
import com.sougata.domain.domain.status.entity.StatusEntity;
import com.sougata.domain.domain.workFlowAction.enums.WorkFlowMovement;
import com.sougata.domain.file.entity.FileEntity;
import com.sougata.domain.role.entity.RoleEntity;
import com.sougata.domain.shared.MasterEntity;
import com.sougata.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "job_workflow_history")
public class JobWorkFlowHistoryEntity implements MasterEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "job_id")
    private JobEntity job;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "assigner_id")
    private UserEntity assigner;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "assignee_id")
    private UserEntity assignee;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "status_id")
    private StatusEntity status;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "file_id")
    private FileEntity file;

    @Column(columnDefinition = "TEXT")
    private String comments;

    @Transient
    @Enumerated(EnumType.STRING)
    private WorkFlowMovement movement;

    @Transient
    private RoleEntity targetRole;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
