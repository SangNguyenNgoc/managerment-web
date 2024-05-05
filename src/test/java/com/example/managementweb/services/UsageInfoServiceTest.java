package com.example.managementweb.services;

import com.example.managementweb.models.dtos.usageInfo.UsageInfoBookingDto;
import com.example.managementweb.models.dtos.usageInfo.UsageInfoBookingRequestDto;
import com.example.managementweb.models.dtos.usageInfo.UsageInfoBorrowDto;
import com.example.managementweb.util.AppUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SpringJUnitWebConfig
class UsageInfoServiceTest {
    @Autowired
    UsageInfoService usageInfoService;

    @Autowired
    private AppUtil appUtil;

    @Test
    void testBorrow(){
//        UsageInfoBorrowDto usageInfoBorrowDto = usageInfoService.borrowDevice("a","120191");
//        System.out.println(appUtil.toJson(usageInfoBorrowDto));
    }

    @Test
    void testReturn(){
        UsageInfoBorrowDto usageInfoBorrowDto = usageInfoService.returnDevice(19L);
        System.out.println(appUtil.toJson(usageInfoBorrowDto));
    }

    @Test
    void testBooking(){
        UsageInfoBookingRequestDto requestDto = UsageInfoBookingRequestDto.builder()
                .deviceId(120191L)
                .personId(1120010007L)
                .bookingTime(LocalDateTime.parse("2024-04-25T13:00:00"))
                .build();
        UsageInfoBookingDto usageInfoBookingDto = usageInfoService.bookingDevice(requestDto);
        System.out.println(appUtil.toJson(usageInfoBookingDto));
    }
}