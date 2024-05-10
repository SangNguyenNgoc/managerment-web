package com.example.managementweb.services;

import com.example.managementweb.models.dtos.statistic.CountPerDate;
import com.example.managementweb.models.dtos.statistic.DeviceBorrowingStatByTime;
import com.example.managementweb.repositories.PenalizeRepository;
import com.example.managementweb.repositories.UsageInfoRepository;
import com.example.managementweb.services.interfaces.IStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticService implements IStatisticService {

    private final UsageInfoRepository usageInfoRepository;

    private final PenalizeRepository penalizeRepository;

    @Override
    public List<CountPerDate> statisticCheckinPerDay(LocalDate date, String department) {
        List<CountPerDate> checkInCounts;
        if (department != null) {
            checkInCounts = usageInfoRepository.getCountCheckInPerDateInDay(
                    date.getDayOfMonth(), date.getMonth().getValue(), date.getYear(), department
            );
        } else {
            checkInCounts = usageInfoRepository.getCountCheckInPerDateInDay(
                    date.getDayOfMonth(), date.getMonth().getValue(), date.getYear()
            );
        }List<CountPerDate> finalCheckInCounts = new ArrayList<>();
        for (int i=0;i<=23;i++) {
            final int hour = i;
            Optional<CountPerDate> countPerDate = checkInCounts.stream()
                    .filter(item -> item.getDateTime().getHour() == hour)
                    .findFirst();
            if(countPerDate.isPresent()) {
                finalCheckInCounts.add(countPerDate.get());
            } else {
                finalCheckInCounts.add(
                        CountPerDate.builder()
                                .dateTime(LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), hour, 0))
                                .count(0L)
                                .build()
                );
            }
        }
        return finalCheckInCounts;
    }

    @Override
    public List<CountPerDate> statisticCheckinPerMonth(LocalDate date, String department) {
        List<LocalDate> dayOfMonth = getDayOfMonth(date.getMonth().getValue(), date.getYear());
        List<CountPerDate> checkInCounts;
        if (department != null) {
            checkInCounts = usageInfoRepository.getCountCheckInPerDateInMonth(date.getMonth().getValue(), date.getYear(), department);
        } else {
            checkInCounts = usageInfoRepository.getCountCheckInPerDateInMonth(date.getMonth().getValue(), date.getYear());
        }
        List<CountPerDate> finalCheckInCounts = checkInCounts;
        return dayOfMonth.stream()
                .map(day -> {
                    Optional<CountPerDate> checkIn = finalCheckInCounts.stream()
                            .filter(checkInCount -> checkInCount.getDateTime().toLocalDate().equals(day))
                            .findFirst();
                    return checkIn.orElse(CountPerDate.builder()
                            .dateTime(day.atStartOfDay())
                            .count(0L)
                            .build());
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<CountPerDate> statisticCheckinPerYear(LocalDate date, String department) {
        List<CountPerDate> checkInCounts;
        if (department != null) {
            checkInCounts = usageInfoRepository.getCountCheckInPerDateInYear(date.getYear(), department);
        } else {
            checkInCounts = usageInfoRepository.getCountCheckInPerDateInYear(date.getYear());
        }List<CountPerDate> finalCheckInCounts = new ArrayList<>();
        for (int i=1;i<=12;i++) {
            final int month = i;
            Optional<CountPerDate> countPerDate = checkInCounts.stream()
                    .filter(item -> item.getDateTime().getMonth().getValue() == month)
                    .findFirst();
            if(countPerDate.isPresent()) {
                finalCheckInCounts.add(countPerDate.get());
            } else {
                finalCheckInCounts.add(
                        CountPerDate.builder()
                                .dateTime(LocalDateTime.of(date.getYear(), month, 1, 0, 0))
                                .count(0L)
                                .build()
                );
            }
        }
        return finalCheckInCounts;
    }

    @Override
    public List<CountPerDate> statisticPenalizePerMonthNotPresent(LocalDate date) {
        List<LocalDate> dayOfMonth = getDayOfMonth(date.getMonth().getValue(), date.getYear());
        List<CountPerDate> checkInCounts = penalizeRepository.countPerMonthNotPresent(date.getYear(), date.getMonth().getValue());

        return dayOfMonth.stream()
                .map(day -> {
                    Optional<CountPerDate> checkIn = checkInCounts.stream()
                            .filter(checkInCount -> checkInCount.getDateTime().toLocalDate().equals(day))
                            .findFirst();
                    return checkIn.orElse(CountPerDate.builder()
                            .dateTime(day.atStartOfDay())
                            .count(0L)
                            .build());
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<CountPerDate> statisticPenalizePerMonthIsPresent(LocalDate date) {
        List<LocalDate> dayOfMonth = getDayOfMonth(date.getMonth().getValue(), date.getYear());
        List<CountPerDate> checkInCounts = penalizeRepository.countPerMonthIsPresent(date.getYear(), date.getMonth().getValue());

        return dayOfMonth.stream()
                .map(day -> {
                    Optional<CountPerDate> checkIn = checkInCounts.stream()
                            .filter(checkInCount -> checkInCount.getDateTime().toLocalDate().equals(day))
                            .findFirst();
                    return checkIn.orElse(CountPerDate.builder()
                            .dateTime(day.atStartOfDay())
                            .count(0L)
                            .build());
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<CountPerDate> statisticPenalizePerYearNotPresent(int year) {
        List<CountPerDate> checkInCounts = penalizeRepository.countPerYearNotPresent(year);
        List<CountPerDate> finalCheckInCounts = new ArrayList<>();
        for (int i=1;i<=12;i++) {
            final int month = i;
            Optional<CountPerDate> countPerDate = checkInCounts.stream()
                    .filter(item -> item.getDateTime().getMonth().getValue() == month)
                    .findFirst();
            if(countPerDate.isPresent()) {
                finalCheckInCounts.add(countPerDate.get());
            } else {
                finalCheckInCounts.add(
                        CountPerDate.builder()
                                .dateTime(LocalDateTime.of(year, month, 1, 0, 0))
                                .count(0L)
                                .build()
                );
            }
        }
        return finalCheckInCounts;
    }

    @Override
    public List<CountPerDate> statisticPenalizePerYearIsPresent(int year) {
        List<CountPerDate> checkInCounts = penalizeRepository.countPerYearIsPresent(year);
        List<CountPerDate> finalCheckInCounts = new ArrayList<>();
        for (int i=1;i<=12;i++) {
            final int month = i;
            Optional<CountPerDate> countPerDate = checkInCounts.stream()
                    .filter(item -> item.getDateTime().getMonth().getValue() == month)
                    .findFirst();
            if(countPerDate.isPresent()) {
                finalCheckInCounts.add(countPerDate.get());
            } else {
                finalCheckInCounts.add(
                        CountPerDate.builder()
                                .dateTime(LocalDateTime.of(year, month, 1, 0, 0))
                                .count(0L)
                                .build()
                );
            }
        }
        return finalCheckInCounts;
    }

    public List<LocalDate> getDayOfMonth(int month, int year) {
        List<LocalDate> daysOfMonth = new ArrayList<>();
        LocalDate currentDate = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = LocalDate.of(year, month, currentDate.lengthOfMonth());
        while (!currentDate.isAfter(lastDayOfMonth)) {
            daysOfMonth.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }
        return daysOfMonth;
    }

    @Override
    public List<DeviceBorrowingStatByTime> statisticDevicePerMonth(int month, int year) {
        return usageInfoRepository.countDevicesBorrowedInMonth(year, month);
    }

    @Override
    public List<DeviceBorrowingStatByTime> statisticDevicePerYear(int year) {
        return usageInfoRepository.countDevicesBorrowedInYear(year);
    }
}
