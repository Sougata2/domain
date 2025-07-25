package com.sougata.domain.domain.activity.entity;

import com.sougata.domain.domain.devices.entity.DeviceEntity;
import com.sougata.domain.domain.specification.entity.SpecificationEntity;
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
@Table(name = "activities")
public class ActivityEntity implements MasterEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "activity_specification_map",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "specification_id")
    )
    private Set<SpecificationEntity> specifications;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "device_activity_map", joinColumns = @JoinColumn(name = "activity_id"), inverseJoinColumns = @JoinColumn(name = "device_id"))
    private Set<DeviceEntity> devices;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "sub_service_activity_map", joinColumns = @JoinColumn(name = "sub_service_id"), inverseJoinColumns = @JoinColumn(name = "activity_id"))
    private Set<SubServiceEntity> subServices;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "ActivityEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specifications=" + (specifications != null
                ? specifications.stream()
                .map(spec -> spec != null && spec.getName() != null ? spec.getName() : "null")
                .toList()
                : "null") +
                ", devices=" + (devices != null ? devices.stream().map(d -> d != null ? d.getName() : "null").toList() : "null") +
                ", subServices=" + (subServices != null ? subServices.stream().map(s -> s != null ? s.getName() : "null") : "null") +
                ", createdAt=" + createdAt +
                '}';
    }
}
