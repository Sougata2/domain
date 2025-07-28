package com.sougata.domain.domain.formStages.entity;

import com.sougata.domain.domain.forms.entity.FormEntity;
import com.sougata.domain.menu.entity.MenuEntity;
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
@Table(name = "form_stages")
public class FormStageEntity implements MasterEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer stageOrder;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "form_id")
    private FormEntity form;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "menu_id")
    private MenuEntity menu;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "FormStageEntity{" +
                "id=" + id +
                "menu=" + (menu != null ? menu.getName() : "") +
                ", order=" + (stageOrder != null ? stageOrder : "") +
                ", form=" + (form != null ? form.getName() : "null") +
                ", createdAt=" + (createdAt != null ? createdAt.toString() : "null") +
                ", updatedAt=" + (updatedAt != null ? updatedAt.toString() : "null") +
                '}';
    }

}
