package com.example.managementweb.models.dtos.person;

import com.example.managementweb.models.entities.Role;
import com.example.managementweb.models.entities.PersonEntity;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link PersonEntity}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponseDto implements Serializable {
    private Long id;
    private String name;
    private String department;
    private String email;
    private String profession;
    private String phoneNumber;
    private Boolean status;
    private Role role;
}