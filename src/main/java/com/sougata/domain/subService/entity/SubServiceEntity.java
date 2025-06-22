package com.sougata.domain.subService.entity;

import com.sougata.domain.forms.entity.FormEntity;
import com.sougata.domain.services.entity.ServiceEntity;
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
@Table(name = "sub_services")
public class SubServiceEntity implements MasterEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "subServices", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    Set<ServiceEntity> services;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "form_id")
    private FormEntity form;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
