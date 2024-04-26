package com.example.managementweb.models.dtos.penalize;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.example.managementweb.models.entities.PenalizeEntity}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PenalizeCreateDto {
    @NotBlank(message = "Mã thành viên không được bỏ trống")
    @Pattern(regexp = "\\d", message = "Mã thành viên chỉ được chứa ký tự số")
    Long person;
    @NotBlank(message = "Hình thức xử lí không được để trống")
    String type;
    @Pattern(regexp = "\\d", message = "Tiền chỉ được chứa ký tự số")
    Integer payment;
}
