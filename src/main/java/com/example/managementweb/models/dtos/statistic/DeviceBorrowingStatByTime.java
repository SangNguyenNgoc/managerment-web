package com.example.managementweb.models.dtos.statistic;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceBorrowingStatByTime {

    private Long deviceId;
    private String deviceName;
    private Long timesBorrowed;
}
