package com.sougata.domain.domain.services.entity;

import com.sougata.domain.domain.application.entity.ApplicationEntity;
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
@Table(name = "services")
public class ServiceEntity implements MasterEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "service_sub_service_map", joinColumns = @JoinColumn(name = "service_id"), inverseJoinColumns = @JoinColumn(name = "sub_service_id"))
    private Set<SubServiceEntity> subServices;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "service")
    private Set<ApplicationEntity> applications;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "ServiceEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subServices=" + (subServices != null
                ? subServices.stream()
                .map(sub -> sub != null ? sub.getName() : "null")
                .toList()
                : "null") +
                ", applications=" + (applications != null
                ? applications.stream()
                .map(app -> app != null ? app.getId() : "null")
                .toList()
                : "null") +
                ", createdAt=" + createdAt +
                '}';
    }
}
