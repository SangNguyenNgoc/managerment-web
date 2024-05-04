package com.example.managementweb.services.interfaces;

import com.example.managementweb.models.dtos.statistic.CountPerDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IStatisticService {

    List<CountPerDate> statisticCheckinPerDay(LocalDate date, String department);

    List<CountPerDate> statisticCheckinPerMonth(LocalDate date, String department);

    List<CountPerDate> statisticCheckinPerYear(LocalDate date, String department);

    List<CountPerDate> statisticPenalizePerMonthNotPresent(LocalDate date);

    List<CountPerDate> statisticPenalizePerMonthIsPresent(LocalDate date);

    List<CountPerDate> statisticPenalizePerYearNotPresent(int year);

    List<CountPerDate> statisticPenalizePerYearIsPresent(int year);

}
