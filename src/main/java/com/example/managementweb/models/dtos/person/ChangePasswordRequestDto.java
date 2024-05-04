package com.example.managementweb.models.dtos.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequestDto {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
