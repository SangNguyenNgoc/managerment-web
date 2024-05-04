package com.example.managementweb.models.dtos.person;

import com.example.managementweb.models.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * DTO for {@link com.example.managementweb.models.entities.PersonEntity}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonAndUsageDto implements Serializable {
    Long id;
    String name;
    String department;
    String email;
    String profession;
    String phoneNumber;
    Role role;
    List<UsageInfoDto> usageInfos;

    /**
     * DTO for {@link com.example.managementweb.models.entities.UsageInfoEntity}
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UsageInfoDto implements Serializable {
        Long id;
        String bookingTime;
        String borrowTime;
        String returnTime;
        DeviceDto device;

        /**
         * DTO for {@link com.example.managementweb.models.entities.DeviceEntity}
         */
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class DeviceDto implements Serializable {
            Long id;
            String name;
            String description;
        }
    }
}