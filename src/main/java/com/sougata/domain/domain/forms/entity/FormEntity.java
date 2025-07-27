package com.sougata.domain.domain.forms.entity;

import com.sougata.domain.domain.formStages.entity.FormStageEntity;
import com.sougata.domain.domain.mandatoryDocument.entity.MandatoryDocumentsEntity;
import com.sougata.domain.shared.MasterEntity;
import com.sougata.domain.subService.entity.SubServiceEntity;
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

    @OneToMany(mappedBy = "form", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<MandatoryDocumentsEntity> mandatoryDocuments;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "form_stage_map", joinColumns = @JoinColumn(name = "form_id"), inverseJoinColumns = @JoinColumn(name = "stage_id"))
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
                ", mandatoryDocuments=" + (mandatoryDocuments != null
                ? mandatoryDocuments.stream()
                .map(doc -> doc != null ? doc.getName() : "null")
                .toList()
                : "null") +
                ", createdAt=" + createdAt +
                '}';
    }
}
