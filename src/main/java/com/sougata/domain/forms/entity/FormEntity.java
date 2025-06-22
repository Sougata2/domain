package com.sougata.domain.forms.entity;

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
    Set<SubServiceEntity> subServices;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
