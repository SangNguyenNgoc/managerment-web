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
public class UsageInfoBookingDto implements Serializable {
    Long id;
    String bookingTime;
    PersonEntityDto person;
    DeviceEntityDto device;

    /**
     * DTO for {@link com.example.managementweb.models.entities.PersonEntity}
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PersonEntityDto implements Serializable {
        Long id;
        String name;
        String email;
        String phoneNumber;
    }

    /**
     * DTO for {@link com.example.managementweb.models.entities.DeviceEntity}
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeviceEntityDto implements Serializable {
        Long id;
        String name;
        String description;
    }
}