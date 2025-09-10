package com.sougata.domain.domain.job.entity;

import com.sougata.domain.domain.devices.entity.DeviceEntity;
import com.sougata.domain.domain.jobWorkFlowHistory.entity.JobWorkFlowHistoryEntity;
import com.sougata.domain.domain.lab.entity.LabEntity;
import com.sougata.domain.domain.status.entity.StatusEntity;
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
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jobs")
public class JobEntity implements MasterEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "device_id")
    private DeviceEntity device;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "status_id")
    private StatusEntity status;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "assignee_id")
    private UserEntity assignee;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "lab_id")
    private LabEntity lab;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "job")
    private Set<JobWorkFlowHistoryEntity> workFlowHistory;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
