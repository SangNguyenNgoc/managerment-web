package com.example.managementweb.repositories;

import com.example.managementweb.models.entities.DeviceEntity;
import com.example.managementweb.models.entities.UsageInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
}
