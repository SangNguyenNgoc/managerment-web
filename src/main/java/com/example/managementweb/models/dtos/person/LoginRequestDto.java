package com.example.managementweb.models.dtos.person;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.example.managementweb.models.entities.PersonEntity}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto implements Serializable {
    private String username;
    private String password;
}