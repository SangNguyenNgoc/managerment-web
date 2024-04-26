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
    @NotNull(message = "mã thành viên đặt không được để trống")
    @Pattern(regexp = "\\d{10}", message = "Mã thành viên chỉ được chứa 10 ký tự và chỉ được là ký tự số")
    Long personId;
    @NotNull(message = "mã thiết bị không được để trống")
    Long deviceId;

}