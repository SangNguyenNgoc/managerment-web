package com.example.managementweb.models.dtos.person;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonCreateDto {

    @NotBlank(message = "Mã thành viên không được để trống")
    @Pattern(regexp = "\\d{10}", message = "Mã thành viên chỉ được chứa 10 ký tự và chỉ được là ký tự số")
    private String id;

    @Email(message = "Email sai định dạng")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    @NotBlank(message = "Tên người dùng không được để trống")
    private String name;

    @NotBlank(message = "Khoa không được để trống")
    private String department;

    @NotBlank(message = "Ngành không được để trống")
    private String profession;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^[0-9]{10}$", message = "Số điện thoại sai định dạng")
    private String phoneNumber;
}
