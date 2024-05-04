package com.example.managementweb.repositories;

import com.example.managementweb.models.dtos.statistic.CountPerDate;
import com.example.managementweb.models.dtos.statistic.DeviceBorrowingStatByTime;
import com.example.managementweb.models.entities.UsageInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UsageInfoRepository extends JpaRepository<UsageInfoEntity, Long> {
    @Query(value = "select (count(u) > 0) from UsageInfoEntity u where date(u.bookingTime) = ?1 and u.device.id = ?2")
    boolean existsByBookingTime(LocalDate bookingDate, Long deviceId);

    @Query(value = "select (count(u) > 0) from UsageInfoEntity u where date(u.borrowTime) = ?1 and u.device.id = ?2")
    boolean existsByBorrowTime(LocalDate borrowTime,Long deviceId);

    @Query("SELECT u " +
            "FROM UsageInfoEntity u " +
            "WHERE u.borrowTime IS NOT NULL")
    List<UsageInfoEntity> findAllBorrow();

    @Query("SELECT u " +
            "FROM UsageInfoEntity u " +
            "WHERE u.id = :id " +
            "AND u.returnTime IS NULL")
    UsageInfoEntity findByIdForReturn(Long id);

    @Query("SELECT u " +
            "FROM UsageInfoEntity u " +
            "WHERE u.id = :id " +
            "AND u.borrowTime IS NOT NULL")
    UsageInfoEntity findByIdAndBorrow(Long id);

    @Transactional
    @Modifying
    @Query("delete from UsageInfoEntity u where u.bookingTime < ?1 and u.borrowTime is null")
    void deleteByBookingTimeBefore(LocalDateTime bookingTime);

    @Query("SELECT NEW com.example.managementweb.models.dtos.statistic.CountPerDate(u.checkinTime, COUNT(u.id)) " +
            "FROM UsageInfoEntity u " +
            "WHERE MONTH(u.checkinTime) = :month " +
            "AND YEAR(u.checkinTime) = :year " +
            "GROUP BY DATE(u.checkinTime) " +
            "ORDER BY u.checkinTime DESC")
    List<CountPerDate> getCountCheckInPerDateInMonth(
            @Param("month") int month,
            @Param("year") int year
    );

    @Query("SELECT NEW com.example.managementweb.models.dtos.statistic.CountPerDate(u.checkinTime, COUNT(u.id)) " +
            "FROM UsageInfoEntity u " +
            "WHERE MONTH(u.checkinTime) = :month " +
            "AND YEAR(u.checkinTime) = :year " +
            "AND (u.person.department = :department OR u.person.profession = :department) " +
            "GROUP BY DATE(u.checkinTime) " +
            "ORDER BY u.checkinTime DESC")
    List<CountPerDate> getCountCheckInPerDateInMonth(
            @Param("month") int month,
            @Param("year") int year,
            @Param("department") String department
    );

    @Query("SELECT NEW com.example.managementweb.models.dtos.statistic.CountPerDate(u.checkinTime, COUNT(u.id)) " +
            "FROM UsageInfoEntity u " +
            "WHERE DAY(u.checkinTime) = :day " +
            "AND MONTH(u.checkinTime) = :month " +
            "AND YEAR(u.checkinTime) = :year " +
            "GROUP BY HOUR(u.checkinTime) " +
            "ORDER BY u.checkinTime DESC")
    List<CountPerDate> getCountCheckInPerDateInDay(
            @Param("day") int day,
            @Param("month") int month,
            @Param("year") int year
    );

    @Query("SELECT NEW com.example.managementweb.models.dtos.statistic.CountPerDate(u.checkinTime, COUNT(u.id)) " +
            "FROM UsageInfoEntity u " +
            "WHERE DAY(u.checkinTime) = :day " +
            "AND MONTH(u.checkinTime) = :month " +
            "AND YEAR(u.checkinTime) = :year " +
            "AND (u.person.department = :department OR u.person.profession = :department) " +
            "GROUP BY HOUR(u.checkinTime) " +
            "ORDER BY u.checkinTime DESC")
    List<CountPerDate> getCountCheckInPerDateInDay(
            @Param("day") int day,
            @Param("month") int month,
            @Param("year") int year,
            @Param("department") String department
    );

    @Query("SELECT NEW com.example.managementweb.models.dtos.statistic.CountPerDate(u.checkinTime, COUNT(u.id)) " +
            "FROM UsageInfoEntity u " +
            "WHERE YEAR(u.checkinTime) = :year " +
            "GROUP BY MONTH(u.checkinTime) " +
            "ORDER BY u.checkinTime DESC")
    List<CountPerDate> getCountCheckInPerDateInYear(
            @Param("year") int year
    );

    @Query("SELECT NEW com.example.managementweb.models.dtos.statistic.CountPerDate(u.checkinTime, COUNT(u.id)) " +
            "FROM UsageInfoEntity u " +
            "WHERE YEAR(u.checkinTime) = :year " +
            "AND (u.person.department = :department OR u.person.profession = :department) " +
            "GROUP BY MONTH(u.checkinTime) " +
            "ORDER BY u.checkinTime DESC")
    List<CountPerDate> getCountCheckInPerDateInYear(
            @Param("year") int year,
            @Param("department") String department
    );

    @Query("SELECT NEW com.example.managementweb.models.dtos.statistic.DeviceBorrowingStatByTime(u.device.id, u.device.name, COUNT (MONTH (u.borrowTime))) " +
            "FROM UsageInfoEntity u " +
            "WHERE MONTH (u.borrowTime) = :month " +
            "AND YEAR (u.borrowTime) = :year " +
            "AND u.returnTime is null " +
            "GROUP BY u.device.id " +
            "ORDER BY u.device.id")
    List<DeviceBorrowingStatByTime> countDevicesBorrowedInMonthNotReturn(
            @Param("year") int year,
            @Param("month") int month
    );

    @Query("SELECT NEW com.example.managementweb.models.dtos.statistic.DeviceBorrowingStatByTime(u.device.id, u.device.name, COUNT (MONTH (u.borrowTime))) " +
            "FROM UsageInfoEntity u " +
            "WHERE MONTH (u.borrowTime) = :month " +
            "AND YEAR (u.borrowTime) = :year " +
            "AND u.returnTime is not null " +
            "GROUP BY u.device.id " +
            "ORDER BY u.device.id")
    List<DeviceBorrowingStatByTime> countDevicesBorrowedInMonth(
            @Param("year") int year,
            @Param("month") int month
    );


    @Query("SELECT NEW com.example.managementweb.models.dtos.statistic.DeviceBorrowingStatByTime(u.device.id, u.device.name, COUNT (MONTH (u.borrowTime))) " +
            "FROM UsageInfoEntity u " +
            "WHERE YEAR (u.borrowTime) = :year " +
            "AND u.returnTime is null " +
            "GROUP BY u.device.id " +
            "ORDER BY u.device.id")
    List<DeviceBorrowingStatByTime> countDevicesBorrowedInYearNotReturn(
            @Param("year") int year
    );

    @Query("SELECT NEW com.example.managementweb.models.dtos.statistic.DeviceBorrowingStatByTime(u.device.id, u.device.name, COUNT (MONTH (u.borrowTime))) " +
            "FROM UsageInfoEntity u " +
            "WHERE YEAR (u.borrowTime) = :year " +
            "AND u.returnTime is not null " +
            "GROUP BY u.device.id " +
            "ORDER BY u.device.id")
    List<DeviceBorrowingStatByTime> countDevicesBorrowedInYear(
            @Param("year") int year
    );
}
