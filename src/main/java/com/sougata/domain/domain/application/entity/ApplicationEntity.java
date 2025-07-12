package com.sougata.domain.domain.application.entity;

import com.sougata.domain.domain.devices.entity.DeviceEntity;
import com.sougata.domain.domain.document.entity.DocumentEntity;
import com.sougata.domain.domain.lab.entity.LabEntity;
import com.sougata.domain.domain.quotation.entity.QuotationEntity;
import com.sougata.domain.domain.services.entity.ServiceEntity;
import com.sougata.domain.domain.status.entity.StatusEntity;
import com.sougata.domain.shared.MasterEntity;
import com.sougata.domain.subService.entity.SubServiceEntity;
import com.sougata.domain.user.entity.UserEntity;
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
@Table(name = "applications")
public class ApplicationEntity implements MasterEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * <p>
     *
     * @GeneratedValue(generator = "application-id-generator")
     * @GenericGenerator( name = "application-id-generator",
     * strategy = "com.sougata.domain.shared.ApplicationIdGenerator"
     * )
     * @Column(nullable = false, updatable = false)
     * </p>
     */
    @Column(unique = true)
    private String referenceNumber;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceEntity service;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.DETACH})
    @JoinColumn(name = "sub_service_id", nullable = false)
    private SubServiceEntity subService;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.DETACH})
    @JoinColumn(name = "lab_id")
    private LabEntity lab;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "application")
    private Set<DeviceEntity> devices;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "application")
    private Set<DocumentEntity> documents;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "application")
    private QuotationEntity quotation;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "status_id", nullable = false)
    private StatusEntity status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "ApplicationEntity{" +
                "Id='" + id + '\'' +
                ", referenceNumber='" + referenceNumber + '\'' +
                ", user=" + (user != null ? user.getFirstName() + " " + user.getLastName() : "null") +
                ", service=" + (service != null ? service.getName() : "null") +
                ", subService=" + (subService != null ? subService.getName() : "null") +
                ", lab=" + (lab != null ? lab.getName() : "null") +
                ", devices=" + (devices != null ? devices.stream().map(d -> d != null ? d.getName() : "null").toList() : "null") +
                ", documents=" + (documents != null ? documents.stream().map(d -> d != null ? d.getName() : "null").toList() : "null") +
                ", quotation=" + (quotation != null ? quotation.toString() : "null") +
                ", status=" + (status != null ? status.getName() : "null") +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
