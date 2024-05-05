package com.example.managementweb.models.dtos.usageInfo;

import com.example.managementweb.models.dtos.person.PersonResponseDto;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.managementweb.models.entities.UsageInfoEntity}
 */
@Value
public class CheckInResponseDto implements Serializable {
    String checkinTime;
    PersonResponseDto person;
}