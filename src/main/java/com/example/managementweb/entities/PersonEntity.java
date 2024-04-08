package com.example.managementweb.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "persons")
public class PersonEntity {
    @Id
    @Column(name = "id", nullable = false, length = 20)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "department", length = 100, nullable = false)
    private String department;

    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 500, nullable = false)
    private String password;

    @Column(name = "profession", length = 100, nullable = false)
    private String profession;

    @Column(name = "phone_number", length = 10, nullable = false)
    private String phoneNumber;

    @Column(name = "status", nullable = false)
    private Boolean status;


    @OneToMany(
            mappedBy = "person",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    private Set<PenalizeEntity> penalties;

    @OneToMany(
            mappedBy = "person",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    private Set<UsageInfoEntity> usageInfos;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "department = " + department + ", " +
                "email = " + email + ", " +
                "password = " + password + ", " +
                "profession = " + profession + ", " +
                "phoneNumber = " + phoneNumber + ", " +
                "status = " + status + ")";
    }
}
