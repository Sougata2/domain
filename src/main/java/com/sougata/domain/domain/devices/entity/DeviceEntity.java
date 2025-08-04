package com.sougata.domain.domain.devices.entity;

import com.sougata.domain.domain.activity.entity.ActivityEntity;
import com.sougata.domain.domain.application.entity.ApplicationEntity;
import com.sougata.domain.domain.specification.entity.SpecificationEntity;
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
@Table(name = "devices")
public class DeviceEntity implements MasterEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Long height;

    @Column
    private String heightUnit;

    @Column
    private Long weight;

    @Column
    private String weightUnit;

    @Column
    private Long length;

    @Column
    private String lengthUnit;

    @Column
    private Long quantity;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "device_activity_map",
            joinColumns = @JoinColumn(name = "device_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    private Set<ActivityEntity> activities;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "device_specification_map",
            joinColumns = @JoinColumn(name = "device_id"),
            inverseJoinColumns = @JoinColumn(name = "specification_id")
    )
    private Set<SpecificationEntity> specifications;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.PERSIST})
    @JoinColumn(name = "application_id")
    private ApplicationEntity application;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @Override
    public String toString() {
        return "DeviceEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", activities=" + (activities != null
                ? activities.stream()
                .map(act -> act != null ? act.getName() : "null")
                .toList()
                : "null") +
                ", specifications=" + (specifications != null
                ? specifications.stream()
                .map(spec -> spec != null ? spec.getName() : "null")
                .toList()
                : "null") +
                ", application=" + (application != null ? application.getId() : "null") +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
