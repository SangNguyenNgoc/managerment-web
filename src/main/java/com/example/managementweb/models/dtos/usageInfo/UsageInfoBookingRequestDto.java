package com.example.managementweb.models.dtos.usageInfo;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class UsageInfoBookingRequestDto implements Serializable {
    @NotNull(message = "thời gian đặt trước không để trống")
    @Future(message = "Thời gian đặt phải là ngày của tương lai")
    LocalDateTime bookingTime;
    Long personId;
    @NotNull(message = "mã thiết bị không được để trống")
    Long deviceId;
    String deviceName;

}