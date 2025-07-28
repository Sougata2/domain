package com.sougata.domain.domain.formStages.entity;

import com.sougata.domain.domain.forms.entity.FormEntity;
import com.sougata.domain.menu.entity.MenuEntity;
import com.sougata.domain.shared.MasterEntity;
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
@Table(name = "form_stages")
public class FormStageEntity implements MasterEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer stageOrder;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "form_stage_map", joinColumns = @JoinColumn(name = "stage_id"), inverseJoinColumns = @JoinColumn(name = "form_id"))
    private Set<FormEntity> forms;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "menu_id")
    private MenuEntity menu;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "FormStageEntity{" +
                "id=" + id +
                ", order=" + (stageOrder != null ? stageOrder : "") +
                ", forms=" + (forms != null ? forms.stream()
                .map(f -> f.getId() != null ? f.getId().toString() : "null")
                .toList()
                : "null") +
                ", createdAt=" + (createdAt != null ? createdAt.toString() : "null") +
                ", updatedAt=" + (updatedAt != null ? updatedAt.toString() : "null") +
                '}';
    }

}
