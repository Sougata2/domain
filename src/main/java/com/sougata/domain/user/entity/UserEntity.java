package com.sougata.domain.user.entity;

import com.sougata.domain.domain.application.entity.ApplicationEntity;
import com.sougata.domain.role.entity.RoleEntity;
import com.sougata.domain.shared.MasterEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity implements MasterEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String password;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "user_role_map",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "default_role_id")
    private RoleEntity defaultRole;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "user")
    private Set<ApplicationEntity> applications;

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + (roles != null ? roles.stream()
                .map(role -> role != null ? role.getName() : "null")
                .toList() : "null") +
                ", defaultRole=" + (defaultRole != null ? defaultRole.getName() : "null") +
                ", applications=" + (applications != null ? applications.stream().map(a -> a != null ? a.getId() : null).toList() : "null") +
                '}';
    }

    @PrePersist
    protected void onCreate() {
        this.password = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password);
    }

    @PreUpdate
    protected void onUpdate() {
        if (this.password != null && !this.password.startsWith("{bcrypt}")) {
            this.password = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password);
        }
    }
}
