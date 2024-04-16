package com.example.managementweb.models.dtos.person;

import com.example.managementweb.models.entities.PersonEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link PersonEntity}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonUpdateDto implements Serializable {
    @NotBlank(message = "Mã thành viên không được để trống")
    @Pattern(regexp = "\\d{10}", message = "Mã thành viên chỉ được chứa 10 ký tự và chỉ được là ký tự số")
    private String id;

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