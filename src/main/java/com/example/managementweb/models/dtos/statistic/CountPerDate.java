package com.example.managementweb.models.dtos.statistic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountPerDate {
    private LocalDateTime dateTime;
    private Long count;
}
