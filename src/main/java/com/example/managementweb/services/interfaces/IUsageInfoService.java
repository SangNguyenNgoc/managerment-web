package com.example.managementweb.services.interfaces;

import com.example.managementweb.models.dtos.usageInfo.CheckInResponseDto;
import com.example.managementweb.models.dtos.usageInfo.UsageInfoBookingDto;
import com.example.managementweb.models.dtos.usageInfo.UsageInfoBookingRequestDto;
import com.example.managementweb.models.dtos.usageInfo.UsageInfoBorrowDto;

import java.time.LocalDateTime;
import java.util.List;

public interface IUsageInfoService {

    UsageInfoBorrowDto borrowDevice(Long userId, Long deviceId);

    List<UsageInfoBorrowDto> getAllBorrow();

    List<UsageInfoBookingDto> getAllBooking();

    UsageInfoBorrowDto returnDevice(Long Id);

    public List<CheckInResponseDto> getAllCheckIn();

    Boolean deleteBorrow(Long id);

    void cancelBooking(Long id);

    UsageInfoBookingDto bookingDevice(UsageInfoBookingRequestDto requestDto);

    Boolean checkIn(Long personId);

    UsageInfoBookingDto getById(Long id);

    UsageInfoBorrowDto getBorrowById(Long id);

    boolean existByBookingTime(LocalDateTime time, Long deviceId);

    boolean existByBorrowTime(LocalDateTime time, Long deviceId);

    void deleteUsageByTime();

    void getDevice(Long id);

    boolean checkBooking(Long id);
}
