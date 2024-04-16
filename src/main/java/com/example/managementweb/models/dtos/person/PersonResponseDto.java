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
    Long id;
    String name;
    String department;
    String email;
    String profession;
    String phoneNumber;
    Role role;
}