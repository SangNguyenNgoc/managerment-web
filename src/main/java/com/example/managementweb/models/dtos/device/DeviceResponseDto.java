package com.example.managementweb.models.dtos.device;

import com.example.managementweb.models.entities.DeviceEntity;
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

public class DeviceResponseDto implements Serializable {
    Long id;
    String name;
    String description;
}
