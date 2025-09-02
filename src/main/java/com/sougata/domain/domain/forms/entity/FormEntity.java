package com.sougata.domain.domain.forms.entity;

import com.sougata.domain.domain.formStages.entity.FormStageEntity;
import com.sougata.domain.domain.subService.entity.SubServiceEntity;
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
@Table(name = "forms")
public class FormEntity implements MasterEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "form", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<SubServiceEntity> subServices;

    @OneToMany(mappedBy = "form", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<FormStageEntity> stages;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "FormEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subServices=" + (subServices != null
                ? subServices.stream()
                .map(sub -> sub != null ? sub.getName() : "null")
                .toList()
                : "null") +
                ", stages=" + (stages != null ? stages.stream().map(s -> s != null ? s.getMenu().getName() : "null").toList() : "null") +
                ", createdAt=" + createdAt +
                '}';
    }
}
