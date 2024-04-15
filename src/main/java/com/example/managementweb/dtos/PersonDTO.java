package com.example.managementweb.dtos;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDTO {
    @NotNull(message = "id must not be null")
    @Size(max = 10, min = 10, message = "ID must be 10 numbers")
    private String id;

    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "password must not be blank")
    private String password;

    @NotBlank(message = "name must not be blank")
    private String name;

    @NotBlank(message = "department must not be null")
    private String department;

    @NotBlank(message = "profession must not be null")
    private String profession;

    @NotBlank(message = "phoneNumber must not be null")
    private String phoneNumber;
}
