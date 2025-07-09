package com.sougata.domain.domain.specification.entity;

import com.sougata.domain.domain.activity.entity.ActivityEntity;
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
@Table(name = "specifications")
public class SpecificationEntity implements MasterEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "activity_specification_map",
            joinColumns = @JoinColumn(name = "specification_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    private Set<ActivityEntity> activities;

    @Column
    private Double price;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "SpecificationEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", activities=" + (activities != null
                ? activities.stream()
                .map(act -> act != null && act.getName() != null ? act.getName() : "null")
                .toList()
                : "null") +
                ", price=" + price +
                ", createdAt=" + createdAt +
                '}';
    }

}
