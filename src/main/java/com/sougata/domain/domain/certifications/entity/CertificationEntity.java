package com.sougata.domain.domain.certifications.entity;

import com.sougata.domain.domain.application.entity.ApplicationEntity;
import com.sougata.domain.file.entity.FileEntity;
import com.sougata.domain.shared.MasterEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "certificates")
public class CertificationEntity implements MasterEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String certificateNumber;

    @Column
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    private FileEntity file;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    private ApplicationEntity application;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
