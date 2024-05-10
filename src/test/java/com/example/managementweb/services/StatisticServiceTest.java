package com.example.managementweb.services;

import com.example.managementweb.models.dtos.statistic.CountPerDate;
import com.example.managementweb.util.AppUtil;
import org.apache.coyote.http11.filters.IdentityOutputFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StatisticServiceTest {

    @Autowired
    StatisticService statisticService;

    @Autowired
    AppUtil appUtil;

    @Test
    void statisticCheckinPerDay() {
        LocalDateTime dateTime = LocalDateTime.of(2024, Month.MAY,6, 0, 0);
        List<CountPerDate> result = statisticService.statisticCheckinPerDay(dateTime.toLocalDate(), null);
        System.out.println(appUtil.toJson(result));
    }

    @Test
    void statisticCheckinPerMonth() {
        LocalDateTime dateTime = LocalDateTime.of(2024, Month.APRIL,1, 0, 0);
        List<CountPerDate> result = statisticService.statisticCheckinPerMonth(dateTime.toLocalDate(), "CNTT");
        System.out.println(appUtil.toJson(result));
    }

    @Test
    void statisticCheckinPerYear() {
        LocalDateTime dateTime = LocalDateTime.of(2024, Month.APRIL,1, 0, 0);
        List<CountPerDate> result = statisticService.statisticCheckinPerYear(dateTime.toLocalDate(), null);
        System.out.println(appUtil.toJson(result));
    }
}