package com.example.managementweb.models.dtos.usageInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRequestDto {
    private Long deviceId;
    private String deviceName;
    private Long userId;
}
