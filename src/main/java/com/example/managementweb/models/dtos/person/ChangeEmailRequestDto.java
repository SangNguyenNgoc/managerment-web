package com.example.managementweb.models.dtos.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeEmailRequestDto {
    private String otp;
    private String password;
}
