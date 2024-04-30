package com.example.managementweb.services.interfaces;

import com.example.managementweb.models.dtos.usageInfo.UsageInfoBookingDto;
import com.example.managementweb.models.dtos.usageInfo.UsageInfoBookingRequestDto;
import com.example.managementweb.models.dtos.usageInfo.UsageInfoBorrowDto;

import java.time.LocalDateTime;
import java.util.List;

public interface IUsageInfoService {

    UsageInfoBorrowDto borrowDevice(Long userId, Long deviceId);

    List<UsageInfoBorrowDto> getAllBorrow();

    UsageInfoBorrowDto returnDevice(String Id);

    Boolean deleteBorrow(Long id);

    UsageInfoBookingDto bookingDevice(UsageInfoBookingRequestDto requestDto);

    Boolean checkIn(Long personId);

    UsageInfoBookingDto getById(Long id);

    boolean existByBookingTime(LocalDateTime time, Long deviceId);

    boolean existByBorrowTime(LocalDateTime time, Long deviceId);
}
