package com.sougata.domain.domain.lab.entity;

import com.sougata.domain.domain.application.entity.ApplicationEntity;
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
@Table(name = "labs")
public class LabEntity implements MasterEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "lab", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<ApplicationEntity> applications;

    @CreationTimestamp
    private LocalDateTime createdAt;


    @Override
    public String toString() {
        return "LabEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", applications=" + (applications != null
                ? applications.stream()
                .map(app -> app != null ? app.getId() : "null")
                .toList()
                : "null") +
                ", createdAt=" + createdAt +
                '}';
    }
}
