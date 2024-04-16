package com.example.managementweb.models.dtos.device;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.example.managementweb.models.entities.DeviceEntity}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceCreateDto implements Serializable {
    @NotBlank(message = "Mã thiết bị không được để trống")
    @Pattern(regexp = "\\d", message = "Mã thiết bị chỉ được chứa ký tự số")
    String id;
    @NotBlank(message = "Tên thiết bị không được bỏ trống")
    String name;
    @NotBlank(message = "Mô tả thiết bị không được để trống")
    String description;
}