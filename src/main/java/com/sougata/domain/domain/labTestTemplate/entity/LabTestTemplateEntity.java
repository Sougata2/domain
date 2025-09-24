package com.sougata.domain.domain.labTestTemplate.entity;

import com.sougata.domain.domain.subService.entity.SubServiceEntity;
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
@Table(name = "lab_test_templates")
public class LabTestTemplateEntity implements MasterEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(columnDefinition = "TEXT")
    private String header;

    @Column(columnDefinition = "TEXT")
    private String mergeData;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "test_template_sub_service_map", joinColumns = @JoinColumn(name = "test_template_id"), inverseJoinColumns = @JoinColumn(name = "sub_serivce_id"))
    private Set<SubServiceEntity> subServices;

    @Transient
    private Integer columnCount;

    @Transient
    private String headerRange;

    @Transient
    private String defaultSelection;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
