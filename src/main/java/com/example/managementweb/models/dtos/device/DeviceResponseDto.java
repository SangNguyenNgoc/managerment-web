package com.example.managementweb.models.dtos.device;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.managementweb.models.entities.DeviceEntity}
 */
@Value
public class DeviceResponseDto implements Serializable {
    Long id;
    String name;
    String description;
}