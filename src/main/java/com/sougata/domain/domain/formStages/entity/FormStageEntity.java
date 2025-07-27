package com.sougata.domain.domain.formStages.entity;

import com.sougata.domain.domain.forms.entity.FormEntity;
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
    private String name;

    @Column
    private String url;

    @Column
    private Integer order;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "form_stage_map", joinColumns = @JoinColumn(name = "stage_id"), inverseJoinColumns = @JoinColumn(name = "form_id"))
    private Set<FormEntity> forms;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "FormStageEntity{" +
                "id=" + id +
                ", name='" + (name != null ? name : "") + '\'' +
                ", url='" + (url != null ? url : "") + '\'' +
                ", order=" + (order != null ? order : "") +
                ", forms=" + (forms != null ? forms.stream()
                .map(f -> f.getId() != null ? f.getId().toString() : "null")
                .toList()
                : "null") +
                ", createdAt=" + (createdAt != null ? createdAt.toString() : "null") +
                ", updatedAt=" + (updatedAt != null ? updatedAt.toString() : "null") +
                '}';
    }

}
