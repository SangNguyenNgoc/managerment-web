package com.example.managementweb.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "devices")
public class DeviceEntity {
    @Id
    @Column(name = "id", nullable = false, length = 20)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @OneToMany(
            mappedBy = "device",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    private Set<UsageInfoEntity> usageInfos;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "description = " + description + ")";
    }
}
