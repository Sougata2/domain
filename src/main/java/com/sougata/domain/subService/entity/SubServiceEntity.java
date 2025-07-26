package com.sougata.domain.subService.entity;

import com.sougata.domain.domain.activity.entity.ActivityEntity;
import com.sougata.domain.domain.application.entity.ApplicationEntity;
import com.sougata.domain.domain.forms.entity.FormEntity;
import com.sougata.domain.domain.services.entity.ServiceEntity;
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subService")
    private Set<ApplicationEntity> applications;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "sub_service_activity_map", joinColumns = @JoinColumn(name = "activity_id"), inverseJoinColumns = @JoinColumn(name = "sub_service_id"))
    private Set<ActivityEntity> activities;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "SubServiceEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", services=" + (services != null
                ? services.stream()
                .map(svc -> svc != null ? svc.getName() : "null")
                .toList()
                : "null") +
                ", form=" + (form != null ? form.getName() : "null") +
                ", applications=" + (applications != null
                ? applications.stream()
                .map(app -> app != null ? app.getId() : "null")
                .toList()
                : "null") +
                ", activities=" + (activities != null ? activities.stream().map(a -> a != null ? a.getName() : "null").toList() : "null") +
                ", createdAt=" + createdAt +
                '}';
    }
}
