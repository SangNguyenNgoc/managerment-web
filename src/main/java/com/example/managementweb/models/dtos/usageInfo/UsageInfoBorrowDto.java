package com.example.managementweb.models.dtos.usageInfo;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.managementweb.models.entities.UsageInfoEntity}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsageInfoBorrowDto implements Serializable {
    String borrowTime;
    PersonEntityDto person;
    DeviceEntityDto device;
    String returnTime;

    /**
     * DTO for {@link com.example.managementweb.models.entities.PersonEntity}
     */
    @Value
    public static class PersonEntityDto implements Serializable {
        Long id;
        String name;
        String email;
    }

    /**
     * DTO for {@link com.example.managementweb.models.entities.DeviceEntity}
     */
    @Value
    public static class DeviceEntityDto implements Serializable {
        Long id;
        String name;
        String description;
    }
}