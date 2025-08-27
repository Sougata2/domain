package com.sougata.domain.domain.workflowHistory.entity;

import com.sougata.domain.domain.application.entity.ApplicationEntity;
import com.sougata.domain.domain.status.entity.StatusEntity;
import com.sougata.domain.domain.workFlowAction.enums.WorkFlowMovement;
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
@Table(name = "workflow_history")
public class WorkFlowHistoryEntity implements MasterEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "application_id")
    private ApplicationEntity application;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "assigner_id")
    private UserEntity assigner;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "assignee_id")
    private UserEntity assignee;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "status_id")
    private StatusEntity status;

    /**
     * <h1>NOTE</h1>
     * <p>NOT TO BE UPDATED OR PERSISTED</p>
     *
     */
    @Transient
    @Enumerated(EnumType.STRING)
    private WorkFlowMovement movement;

    /**
     * <h1>NOTE</h1>
     * <p>FOR SEARCHING THE ASSIGNEE BY ROLE WHEN MOVEMENT IS REGRESSIVE/PROGRESSIVE_ONE</p>
     *
     */
    @Transient
    private RoleEntity targetRole;

    @Column(columnDefinition = "TEXT")
    private String comments;


    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
