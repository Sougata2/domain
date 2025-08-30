package com.sougata.domain.domain.workFlowGroup.entity;

import com.sougata.domain.domain.workFlowAction.entity.WorkFlowActionEntity;
import com.sougata.domain.shared.MasterEntity;
import com.sougata.domain.subService.entity.SubServiceEntity;
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
@Table(name = "workflow-group")
public class WorkFlowGroupEntity implements MasterEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}, mappedBy = "workFlowGroup")
    private Set<SubServiceEntity> subServices;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "workflow_group_action_map",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "action_id")
    )
    private Set<WorkFlowActionEntity> actions;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "WorkFlowGroupEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subServices=" + (subServices.isEmpty() ? "null" : subServices.stream().map(s -> s.getName() == null ? "null" : s.getName())) +
                ", actions=" + (actions.isEmpty() ? "null" : actions.stream().map(s -> s.getName() == null ? "null" : s.getName())) +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
