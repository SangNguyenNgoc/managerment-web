package com.example.managementweb.models.dtos.device;

import com.example.managementweb.models.entities.DeviceEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link DeviceEntity}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceUpdateDto implements Serializable {
    @NotBlank(message = "Mã thiết bị không được để trống")
    @Pattern(regexp = "\\d", message = "Mã thiết bị chỉ được chứa ký tự số")
    String id;

    @NotBlank(message = "Tên thiết bị không được bỏ trống")
    String name;

    @NotBlank(message = "Mô tả thiết bị không được để trống")
    String description;

}
