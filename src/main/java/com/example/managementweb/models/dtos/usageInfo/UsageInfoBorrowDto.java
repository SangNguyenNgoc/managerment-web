package com.example.managementweb.models.dtos.usageInfo;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.managementweb.models.entities.UsageInfoEntity}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsageInfoBorrowDto implements Serializable {
    Long id;
    String borrowTime;
    PersonEntityDto person;
    DeviceEntityDto device;
    String returnTime;

    /**
     * DTO for {@link com.example.managementweb.models.entities.PersonEntity}
     */
    @Data
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
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeviceEntityDto implements Serializable {
        Long id;
        String name;
        String description;
    }
}